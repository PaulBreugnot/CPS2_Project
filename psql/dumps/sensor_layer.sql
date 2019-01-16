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
-- Name: sensor_layer; Type: TABLE; Schema: public; Owner: cps2_admin
--

CREATE TABLE public.sensor_layer (
    id integer NOT NULL,
    name text
);


ALTER TABLE public.sensor_layer OWNER TO cps2_admin;

--
-- Name: sensor_layer_id_seq; Type: SEQUENCE; Schema: public; Owner: cps2_admin
--

CREATE SEQUENCE public.sensor_layer_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.sensor_layer_id_seq OWNER TO cps2_admin;

--
-- Name: sensor_layer_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: cps2_admin
--

ALTER SEQUENCE public.sensor_layer_id_seq OWNED BY public.sensor_layer.id;


--
-- Name: sensor_layer id; Type: DEFAULT; Schema: public; Owner: cps2_admin
--

ALTER TABLE ONLY public.sensor_layer ALTER COLUMN id SET DEFAULT nextval('public.sensor_layer_id_seq'::regclass);


--
-- Data for Name: sensor_layer; Type: TABLE DATA; Schema: public; Owner: cps2_admin
--

COPY public.sensor_layer (id, name) FROM stdin;
1	sensoritm
\.


--
-- Name: sensor_layer_id_seq; Type: SEQUENCE SET; Schema: public; Owner: cps2_admin
--

SELECT pg_catalog.setval('public.sensor_layer_id_seq', 1, true);


--
-- Name: sensor_layer sensor_layer_pkey; Type: CONSTRAINT; Schema: public; Owner: cps2_admin
--

ALTER TABLE ONLY public.sensor_layer
    ADD CONSTRAINT sensor_layer_pkey PRIMARY KEY (id);


--
-- PostgreSQL database dump complete
--

