--
-- PostgreSQL database dump
--

-- Dumped from database version 10.4
-- Dumped by pg_dump version 11.1

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: measure_type; Type: TABLE; Schema: public; Owner: cps2_admin
--

CREATE TABLE public.measure_type (
    id integer NOT NULL,
    type text NOT NULL,
    unit text
);


ALTER TABLE public.measure_type OWNER TO cps2_admin;

--
-- Name: measure_type_id_seq; Type: SEQUENCE; Schema: public; Owner: cps2_admin
--

CREATE SEQUENCE public.measure_type_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.measure_type_id_seq OWNER TO cps2_admin;

--
-- Name: measure_type_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: cps2_admin
--

ALTER SEQUENCE public.measure_type_id_seq OWNED BY public.measure_type.id;


--
-- Name: measure_type id; Type: DEFAULT; Schema: public; Owner: cps2_admin
--

ALTER TABLE ONLY public.measure_type ALTER COLUMN id SET DEFAULT nextval('public.measure_type_id_seq'::regclass);


--
-- Data for Name: measure_type; Type: TABLE DATA; Schema: public; Owner: cps2_admin
--

COPY public.measure_type (id, type, unit) FROM stdin;
\.


--
-- Name: measure_type_id_seq; Type: SEQUENCE SET; Schema: public; Owner: cps2_admin
--

SELECT pg_catalog.setval('public.measure_type_id_seq', 1, false);


--
-- Name: measure_type measure_type_pkey; Type: CONSTRAINT; Schema: public; Owner: cps2_admin
--

ALTER TABLE ONLY public.measure_type
    ADD CONSTRAINT measure_type_pkey PRIMARY KEY (id);


--
-- PostgreSQL database dump complete
--

