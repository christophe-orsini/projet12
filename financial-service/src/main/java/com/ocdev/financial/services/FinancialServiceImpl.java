package com.ocdev.financial.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collection;
import java.util.Optional;

import javax.mail.MessagingException;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ocdev.financial.beans.Aircraft;
import com.ocdev.financial.converters.IDtoConverter;
import com.ocdev.financial.dao.FlightRepository;
import com.ocdev.financial.dao.SubscriptionRepository;
import com.ocdev.financial.dto.FlightRecordDto;
import com.ocdev.financial.dto.InvoicePayDto;
import com.ocdev.financial.dto.SubscriptionDto;
import com.ocdev.financial.entities.Flight;
import com.ocdev.financial.entities.Subscription;
import com.ocdev.financial.errors.AlreadyExistsException;
import com.ocdev.financial.errors.EntityNotFoundException;
import com.ocdev.financial.errors.ProxyException;
import com.ocdev.financial.messages.RegisterFlightMessage;
import com.ocdev.financial.proxies.HangarProxy;

@Service
public class FinancialServiceImpl implements FinancialService
{
	@Autowired 
	private EmailService _emailService;
	
	@Autowired 
	private EmailContentBuilder _contentBuilder;
	
	@Value("${financial.subscription.amount}")
	private double _charge;
	
	@Value("${app.param.emailcontact}") 
	private String _emailContact="";
	
	@Value("${app.param.emailsubject}") 
	private String _emailSubject="";
	
	private FlightRepository _flightRepository;
	private SubscriptionRepository _subscriptionRepository;
	private IDtoConverter<Flight, FlightRecordDto> _flightRecordDtoConverter;
	private HangarProxy _hangarProxy;
	
	public FinancialServiceImpl(
			@Autowired FlightRepository flightRepository, 
			@Autowired SubscriptionRepository subscriptionRepository,
			@Autowired IDtoConverter<Flight, FlightRecordDto> flightRecordDtoConverter,
			@Autowired HangarProxy hangarProxy)
	{
		_flightRepository = flightRepository;
		_subscriptionRepository = subscriptionRepository;
		_flightRecordDtoConverter = flightRecordDtoConverter;
		_hangarProxy = hangarProxy;
	}

	@Override
	public Collection<Flight> getAllFlights(String memberId, boolean paid)
	{
		return _flightRepository.findByMemberIdAndClosed(memberId, paid);
	}

	@Override
	public Flight getFlight(long id) throws EntityNotFoundException
	{
		Optional<Flight> flight = _flightRepository.findById(id);
		if (!flight.isPresent()) throw new EntityNotFoundException("Ce vol n'existe pas");
		
		return flight.get();
	}

	@Override
	public Subscription getLastSubscription(String memberId) throws EntityNotFoundException
	{
		Optional<Subscription> subscription = _subscriptionRepository.findLastSubscriptionByMemberId(memberId);
		if (!subscription.isPresent()) throw new EntityNotFoundException("Ce membre n'a pas de cotisation en cours");
		
		return subscription.get();
	}

	@Override
	public Subscription recordSubscription(SubscriptionDto subscriptionDto) throws EntityNotFoundException, AlreadyExistsException
	{
		// TODO check if memberId exists
		if (subscriptionDto.getMemberId() == null) throw new EntityNotFoundException("Ce membre n'existe pas");
		
		LocalDate today = LocalDate.now();
		
		Optional<Subscription> activeSubscription = _subscriptionRepository.findLastSubscriptionByMemberId(subscriptionDto.getMemberId());
		if (activeSubscription.isPresent() 
				&& activeSubscription.get().getValidityDate() != null 
				&& activeSubscription.get().getValidityDate().isAfter(today))
		{
			throw new AlreadyExistsException("Ce membre a déjà une cotisation valide");
		}
		
		double value = subscriptionDto.getAmount();
		if (value < 0) value = _charge;
		
		LocalDate validity = LocalDate.of(today.getYear(), 12, 31);
		
		Subscription subscription = new Subscription(subscriptionDto.getMemberId(), today, value);
		subscription.setValidityDate(validity);
		
		return _subscriptionRepository.save(subscription);
	}

	@Override
	public Flight recordFlight(FlightRecordDto flightDto) throws EntityNotFoundException
	{
		// TODO check if memberId exists
		if (flightDto.getMemberId() == null) throw new EntityNotFoundException("Ce membre n'existe pas");
		
		Flight flight = _flightRecordDtoConverter.convertDtoToEntity(flightDto);
		
		return _flightRepository.save(flight);
	}
	
	@RabbitListener(queues = "${finance.rabbitmq.queue}")
	public void registerEndedFlight(RegisterFlightMessage message) throws EntityNotFoundException, ProxyException, MessagingException
	{
		// TODO check if memberId exists
		if (message.getMemberId() == null) throw new EntityNotFoundException("Ce membre n'existe pas");
		
		//Get aircraft
		Aircraft aircraft = _hangarProxy.getAircraftById(message.getAircraftId());
		
		LocalDateTime flightDate = LocalDateTime.ofInstant(Instant.ofEpochMilli(message.getFlightDate().getTime()), ZoneOffset.UTC);
		double duration = Math.round(message.getDuration() * 10.0)/10.0;
		
		Flight flight = new Flight(message.getMemberId(), message.getDescription(), flightDate.toLocalDate(), duration);
		flight.setAircraft(aircraft.getRegistration() + " " + aircraft.getMake() + " " + aircraft.getModel());
		
		flight.setAmount(flight.getFlightHours() * message.getHourlyRate());
		
		_flightRepository.save(flight);
		
		try
		{
			String emailContent = _contentBuilder.buildInvoiceEmail(message.getGivenName(), message.getFamilyName(), _emailContact, flight);
			_emailService.envoiEmailHtml(
					message.getEmail(),
					emailContent,
					_emailSubject,
					_emailContact);
		}
		catch (Exception e)
		{
			// do nothing
		}
	}

	@Override
	public Flight payInvoice(InvoicePayDto invoiceDto) throws EntityNotFoundException, AlreadyExistsException
	{
		Optional<Flight> invoice = _flightRepository.findById(invoiceDto.getInvoiceNumber());
		if (!invoice.isPresent() || invoice.get().isClosed()) throw new EntityNotFoundException("Cette facture n'existe pas");
		
		double balance = invoice.get().getAmount() - invoice.get().getPayment();
		if(balance <= 0.0) throw new AlreadyExistsException("Cette facture est soldée");
		if(balance < invoiceDto.getAmount()) throw new AlreadyExistsException("Le montant payé est supérieur au solde de la facture");
		
		invoice.get().setPaymentDate(invoiceDto.getPaymentDate());
		invoice.get().setPayment(invoice.get().getPayment() + invoiceDto.getAmount());
			
		balance = invoice.get().getAmount() - invoice.get().getPayment();
		if (balance <= 0.0)
		{
			invoice.get().setClosed(true);
		}
		
		return _flightRepository.save(invoice.get());
	}
}
