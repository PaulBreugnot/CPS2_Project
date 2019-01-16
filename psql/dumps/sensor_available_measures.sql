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
-- Name: sensor_available_measures; Type: TABLE; Schema: public; Owner: cps2_admin
--

CREATE TABLE public.sensor_available_measures (
    id integer NOT NULL,
    id_sensor integer,
    id_measure_type integer
);


ALTER TABLE public.sensor_available_measures OWNER TO cps2_admin;

--
-- Name: sensor_available_measures_id_seq; Type: SEQUENCE; Schema: public; Owner: cps2_admin
--

CREATE SEQUENCE public.sensor_available_measures_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.sensor_available_measures_id_seq OWNER TO cps2_admin;

--
-- Name: sensor_available_measures_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: cps2_admin
--

ALTER SEQUENCE public.sensor_available_measures_id_seq OWNED BY public.sensor_available_measures.id;


--
-- Name: sensor_available_measures id; Type: DEFAULT; Schema: public; Owner: cps2_admin
--

ALTER TABLE ONLY public.sensor_available_measures ALTER COLUMN id SET DEFAULT nextval('public.sensor_available_measures_id_seq'::regclass);


--
-- Data for Name: sensor_available_measures; Type: TABLE DATA; Schema: public; Owner: cps2_admin
--

COPY public.sensor_available_measures (id, id_sensor, id_measure_type) FROM stdin;
\.


--
-- Name: sensor_available_measures_id_seq; Type: SEQUENCE SET; Schema: public; Owner: cps2_admin
--

SELECT pg_catalog.setval('public.sensor_available_measures_id_seq', 1, false);


--
-- Name: sensor_available_measures sensor_available_measures_pkey; Type: CONSTRAINT; Schema: public; Owner: cps2_admin
--

ALTER TABLE ONLY public.sensor_available_measures
    ADD CONSTRAINT sensor_available_measures_pkey PRIMARY KEY (id);


--
-- Name: sensor_available_measures measure_type_FK; Type: FK CONSTRAINT; Schema: public; Owner: cps2_admin
--

ALTER TABLE ONLY public.sensor_available_measures
    ADD CONSTRAINT "measure_type_FK" FOREIGN KEY (id_measure_type) REFERENCES public.measure_type(id);


--
-- Name: sensor_available_measures sensor_FK; Type: FK CONSTRAINT; Schema: public; Owner: cps2_admin
--

ALTER TABLE ONLY public.sensor_available_measures
    ADD CONSTRAINT "sensor_FK" FOREIGN KEY (id_sensor) REFERENCES public.sensor(id);


--
-- PostgreSQL database dump complete
--

