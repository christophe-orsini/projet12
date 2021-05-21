--liquibase formatted sql
--changeset cor:financial-5
INSERT INTO subscription SELECT * FROM membership;
