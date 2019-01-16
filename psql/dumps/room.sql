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
-- Name: room; Type: TABLE; Schema: public; Owner: cps2_admin
--

CREATE TABLE public.room (
    id integer NOT NULL,
    geom public.geometry,
    refoffice text,
    id_layer integer
);


ALTER TABLE public.room OWNER TO cps2_admin;

--
-- Name: ROOM_id_seq; Type: SEQUENCE; Schema: public; Owner: cps2_admin
--

CREATE SEQUENCE public."ROOM_id_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."ROOM_id_seq" OWNER TO cps2_admin;

--
-- Name: ROOM_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: cps2_admin
--

ALTER SEQUENCE public."ROOM_id_seq" OWNED BY public.room.id;


--
-- Name: room id; Type: DEFAULT; Schema: public; Owner: cps2_admin
--

ALTER TABLE ONLY public.room ALTER COLUMN id SET DEFAULT nextval('public."ROOM_id_seq"'::regclass);


--
-- Data for Name: room; Type: TABLE DATA; Schema: public; Owner: cps2_admin
--

COPY public.room (id, geom, refoffice, id_layer) FROM stdin;
1	0106000020E610000001000000010300000001000000070000000838CD87879D1140CEEEA51CC6B646400658E1C3799D1140F01CBECBC2B646405D3CFFBE469D114023FABD71C4B64640E4EDA7C5589D11408BC16FD5C8B646401A08AF13839D114062B9FA6EC7B64640984B4B1E7F9D1140C294CC63C6B646400838CD87879D1140CEEEA51CC6B64640	\N	1
2	0106000020E61000000100000001030000000100000005000000E4EDA7C5589D114034B06FD5C8B646409C9B7DB45C9D11400E8AE0C5C9B646408A07F1EE7C9D114046838AB5C8B646405EA75513799D11401DD606C4C7B64640E4EDA7C5589D114034B06FD5C8B64640	\N	1
3	0106000020E61000000100000001030000000100000005000000E119F3E77C9D11407DC214B5C8B64640835E0BB6869D11405784F663C8B6464088F7E613839D11407FC4F56EC7B6464012653514799D11405AFE68C4C7B64640E119F3E77C9D11407DC214B5C8B64640	\N	1
4	0106000020E6100000010000000103000000010000000D000000747C86CE7B9D1140A0A22BA7C2B646408270AEC7889D1140705F42C0C5B6464029974B95979D114060D7CF46C5B6464014BA154B989D11404DDBC97CC5B64640A10068B5899D1140B25602F5C5B64640BA11EE10929D1140CD98EBFBC7B64640043D7B94AE9D1140990E4B15C7B64640CFBDE862A69D1140D669CB05C5B64640F6CCD5E29F9D1140CD79FF3CC5B6464070F313119F9D1140B769CB05C5B64640F84736CDAD9D11404E6DE18DC4B6464089DD9D9FA09D11401DEE6A71C1B64640747C86CE7B9D1140A0A22BA7C2B64640	\N	1
5	0106000020E6100000010000000103000000010000000500000010244F49B99D1140976127B3C6B64640F15016A9EC9D11406D96B30AC5B646401DCD93C1D69D1140444F3EBABFB64640D756C47DA39D11403876EC63C1B6464010244F49B99D1140976127B3C6B64640	\N	1
6	0106000020E610000001000000010300000001000000050000001A18D681ED9D114017505703C5B64640366DB6F4FD9D11404899CC77C4B64640B7C97EE8E79D114070C5DF28BFB6464061026070D79D11400A4909B3BFB646401A18D681ED9D114017505703C5B64640	\N	1
7	0106000020E61000000100000001030000000100000005000000FB9E50C4AB9D1140F767CD62C6B6464023E7DC0AB49D114024D47D1DC6B64640FF3C7B94AE9D1140C8E052C2C4B64640CEBDE862A69D114078602E05C5B64640FB9E50C4AB9D1140F767CD62C6B64640	\N	1
8	0106000020E61000000100000001030000000100000005000000C0C58E82879D11408DC8E01CC6B6464000C70A207F9D114065686A63C6B6464038ECCCB0869D114020639463C8B646402B85A89B8F9D114008777414C8B64640C0C58E82879D11408DC8E01CC6B64640	\N	1
\.


--
-- Name: ROOM_id_seq; Type: SEQUENCE SET; Schema: public; Owner: cps2_admin
--

SELECT pg_catalog.setval('public."ROOM_id_seq"', 8, true);


--
-- Name: room ROOM_pkey; Type: CONSTRAINT; Schema: public; Owner: cps2_admin
--

ALTER TABLE ONLY public.room
    ADD CONSTRAINT "ROOM_pkey" PRIMARY KEY (id);


--
-- Name: room layer_fk; Type: FK CONSTRAINT; Schema: public; Owner: cps2_admin
--

ALTER TABLE ONLY public.room
    ADD CONSTRAINT layer_fk FOREIGN KEY (id_layer) REFERENCES public.room_layer(id);


--
-- PostgreSQL database dump complete
--

