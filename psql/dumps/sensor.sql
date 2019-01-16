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
-- Name: sensor; Type: TABLE; Schema: public; Owner: cps2_admin
--

CREATE TABLE public.sensor (
    id integer NOT NULL,
    geom public.geometry,
    name text,
    id_layer integer
);


ALTER TABLE public.sensor OWNER TO cps2_admin;

--
-- Name: sensor_id_seq; Type: SEQUENCE; Schema: public; Owner: cps2_admin
--

CREATE SEQUENCE public.sensor_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.sensor_id_seq OWNER TO cps2_admin;

--
-- Name: sensor_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: cps2_admin
--

ALTER SEQUENCE public.sensor_id_seq OWNED BY public.sensor.id;


--
-- Name: sensor id; Type: DEFAULT; Schema: public; Owner: cps2_admin
--

ALTER TABLE ONLY public.sensor ALTER COLUMN id SET DEFAULT nextval('public.sensor_id_seq'::regclass);


--
-- Data for Name: sensor; Type: TABLE DATA; Schema: public; Owner: cps2_admin
--

COPY public.sensor (id, geom, name, id_layer) FROM stdin;
1	0104000020E6100000010000000101000000B245A26E849D1140D4595237C6B64640	\N	1
2	0104000020E61000000100000001010000002C2D7B725C9D1140052122BAC8B64640	\N	1
3	0104000020E61000000100000001010000005173FA046F9D1140D1AA4033C5B64640	\N	1
4	0104000020E6100000010000000101000000FB43A7BD519D114045368E1BC4B64640	\N	1
\.


--
-- Name: sensor_id_seq; Type: SEQUENCE SET; Schema: public; Owner: cps2_admin
--

SELECT pg_catalog.setval('public.sensor_id_seq', 4, true);


--
-- Name: sensor sensor_pkey; Type: CONSTRAINT; Schema: public; Owner: cps2_admin
--

ALTER TABLE ONLY public.sensor
    ADD CONSTRAINT sensor_pkey PRIMARY KEY (id);


--
-- Name: sensor layer_fkey; Type: FK CONSTRAINT; Schema: public; Owner: cps2_admin
--

ALTER TABLE ONLY public.sensor
    ADD CONSTRAINT layer_fkey FOREIGN KEY (id_layer) REFERENCES public.sensor_layer(id);


--
-- PostgreSQL database dump complete
--

