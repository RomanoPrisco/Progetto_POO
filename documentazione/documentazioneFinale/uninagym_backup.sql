--
-- PostgreSQL database dump
--

\restrict ghZhhMY1v1wehPnKPwqnSTvWEwLEfSA1vexfzZnrcYe6u1c324qya2w22SgG8r8

-- Dumped from database version 18.3
-- Dumped by pg_dump version 18.3

-- Started on 2026-06-10 15:55:32

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 895 (class 1247 OID 16592)
-- Name: giornosettimana; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE public.giornosettimana AS ENUM (
    'LUNEDI',
    'MARTEDI',
    'MERCOLEDI',
    'GIOVEDI',
    'VENERDI',
    'SABATO'
);


ALTER TYPE public.giornosettimana OWNER TO postgres;

--
-- TOC entry 889 (class 1247 OID 16484)
-- Name: statoiscrizione; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE public.statoiscrizione AS ENUM (
    'ISCRIZIONE_CREATA',
    'LISTA_ATTESA'
);


ALTER TYPE public.statoiscrizione OWNER TO postgres;

--
-- TOC entry 886 (class 1247 OID 16446)
-- Name: tipoabbonamento; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE public.tipoabbonamento AS ENUM (
    'NORMAL',
    'GOLD'
);


ALTER TYPE public.tipoabbonamento OWNER TO postgres;

--
-- TOC entry 880 (class 1247 OID 16458)
-- Name: tipoaccesso; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE public.tipoaccesso AS ENUM (
    'VALIDO',
    'NON_VALIDO'
);


ALTER TYPE public.tipoaccesso OWNER TO postgres;

--
-- TOC entry 231 (class 1255 OID 16641)
-- Name: applica_pricing_dinamico_gold(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.applica_pricing_dinamico_gold() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
    IF NEW.costomensile IS NULL THEN
        RETURN NEW;
    END IF;

    IF TG_OP = 'INSERT' THEN
        IF NEW.tipo = 'GOLD' THEN
            NEW.costomensile := ROUND((NEW.costomensile * 0.70)::numeric, 2);
        END IF;
    ELSIF TG_OP = 'UPDATE' THEN
        IF NEW.tipo = 'GOLD' AND (OLD.tipo IS DISTINCT FROM 'GOLD') THEN
            NEW.costomensile := ROUND((NEW.costomensile * 0.70)::numeric, 2);
        END IF;
    END IF;

    RETURN NEW;
END;
$$;


ALTER FUNCTION public.applica_pricing_dinamico_gold() OWNER TO postgres;

--
-- TOC entry 230 (class 1255 OID 16639)
-- Name: gestisci_overbooking_iscrizione(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.gestisci_overbooking_iscrizione() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
DECLARE
    v_capienza INTEGER;
    v_iscritti INTEGER;
BEGIN
    SELECT c.capienzamax
    INTO v_capienza
    FROM public.corso c
    WHERE c.id = NEW.corso_id;

    IF v_capienza IS NULL THEN
        RAISE EXCEPTION 'Impossibile iscrivere: corso % inesistente', NEW.corso_id;
    END IF;

    SELECT COUNT(*)
    INTO v_iscritti
    FROM public.iscrizione i
    WHERE i.corso_id = NEW.corso_id
      AND i.stato = 'ISCRIZIONE_CREATA';

    IF v_iscritti >= v_capienza THEN
        NEW.stato := 'LISTA_ATTESA';
    ELSE
        NEW.stato := 'ISCRIZIONE_CREATA';
    END IF;

    RETURN NEW;
END;
$$;


ALTER FUNCTION public.gestisci_overbooking_iscrizione() OWNER TO postgres;

--
-- TOC entry 232 (class 1255 OID 16637)
-- Name: valida_accesso_abbonamento(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.valida_accesso_abbonamento() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
DECLARE
    v_datafine DATE;
BEGIN
    IF NEW.tipo = 'NON_VALIDO' THEN
        RETURN NEW;
    END IF;

    SELECT a.datafine
    INTO v_datafine
    FROM abbonamento a
    WHERE a.cliente_cf = NEW.cliente_cf;

    IF v_datafine IS NULL THEN
        RAISE EXCEPTION 'Accesso non consentito: nessun abbonamento trovato per il cliente %', NEW.cliente_cf;
    END IF;

    IF v_datafine < NEW.datainizio THEN
        RAISE EXCEPTION 'Accesso non consentito: abbonamento scaduto per il cliente %', NEW.cliente_cf;
    END IF;

    RETURN NEW;
END;
$$;


ALTER FUNCTION public.valida_accesso_abbonamento() OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 224 (class 1259 OID 16451)
-- Name: abbonamento; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.abbonamento (
    id integer NOT NULL,
    tipo public.tipoabbonamento,
    datainizio date,
    datafine date,
    costomensile double precision,
    cliente_cf character varying(20)
);


ALTER TABLE public.abbonamento OWNER TO postgres;

--
-- TOC entry 227 (class 1259 OID 16610)
-- Name: abbonamento_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.abbonamento_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.abbonamento_id_seq OWNER TO postgres;

--
-- TOC entry 5091 (class 0 OID 0)
-- Dependencies: 227
-- Name: abbonamento_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.abbonamento_id_seq OWNED BY public.abbonamento.id;


--
-- TOC entry 225 (class 1259 OID 16463)
-- Name: accesso; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.accesso (
    id integer NOT NULL,
    datainizio date,
    tipo public.tipoaccesso,
    cliente_cf character varying(20)
);


ALTER TABLE public.accesso OWNER TO postgres;

--
-- TOC entry 228 (class 1259 OID 16624)
-- Name: accesso_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.accesso_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.accesso_id_seq OWNER TO postgres;

--
-- TOC entry 5092 (class 0 OID 0)
-- Dependencies: 228
-- Name: accesso_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.accesso_id_seq OWNED BY public.accesso.id;


--
-- TOC entry 219 (class 1259 OID 16389)
-- Name: persona; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.persona (
    codicefiscale character varying(20) NOT NULL,
    nome character varying(30),
    cognome character varying(30),
    email character varying(50),
    numerotelefonico character varying(20),
    datanascita date
);


ALTER TABLE public.persona OWNER TO postgres;

--
-- TOC entry 220 (class 1259 OID 16409)
-- Name: cliente; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.cliente (
    codicefiscale character varying(20) NOT NULL,
    dataiscrizione date,
    password character varying(100) NOT NULL
)
INHERITS (public.persona);


ALTER TABLE public.cliente OWNER TO postgres;

--
-- TOC entry 223 (class 1259 OID 16438)
-- Name: corso; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.corso (
    id integer NOT NULL,
    nome character varying(50),
    capienzamax integer,
    orainizio time without time zone,
    giorno public.giornosettimana
);


ALTER TABLE public.corso OWNER TO postgres;

--
-- TOC entry 222 (class 1259 OID 16437)
-- Name: corso_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.corso_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.corso_id_seq OWNER TO postgres;

--
-- TOC entry 5093 (class 0 OID 0)
-- Dependencies: 222
-- Name: corso_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.corso_id_seq OWNED BY public.corso.id;


--
-- TOC entry 226 (class 1259 OID 16489)
-- Name: iscrizione; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.iscrizione (
    id integer NOT NULL,
    dataiscrizione date,
    stato public.statoiscrizione,
    cliente_cf character varying(16) NOT NULL,
    corso_id integer NOT NULL
);


ALTER TABLE public.iscrizione OWNER TO postgres;

--
-- TOC entry 229 (class 1259 OID 16629)
-- Name: iscrizione_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.iscrizione_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.iscrizione_id_seq OWNER TO postgres;

--
-- TOC entry 5094 (class 0 OID 0)
-- Dependencies: 229
-- Name: iscrizione_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.iscrizione_id_seq OWNED BY public.iscrizione.id;


--
-- TOC entry 221 (class 1259 OID 16416)
-- Name: istruttore; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.istruttore (
    codicefiscale character varying(20) NOT NULL,
    specializzazione character varying(50),
    certificazioni character varying(50),
    tariffaoraria double precision,
    corso_id integer
)
INHERITS (public.persona);


ALTER TABLE public.istruttore OWNER TO postgres;

--
-- TOC entry 4899 (class 2604 OID 16611)
-- Name: abbonamento id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.abbonamento ALTER COLUMN id SET DEFAULT nextval('public.abbonamento_id_seq'::regclass);


--
-- TOC entry 4900 (class 2604 OID 16625)
-- Name: accesso id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.accesso ALTER COLUMN id SET DEFAULT nextval('public.accesso_id_seq'::regclass);


--
-- TOC entry 4898 (class 2604 OID 16441)
-- Name: corso id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.corso ALTER COLUMN id SET DEFAULT nextval('public.corso_id_seq'::regclass);


--
-- TOC entry 4901 (class 2604 OID 16630)
-- Name: iscrizione id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.iscrizione ALTER COLUMN id SET DEFAULT nextval('public.iscrizione_id_seq'::regclass);


--
-- TOC entry 5080 (class 0 OID 16451)
-- Dependencies: 224
-- Data for Name: abbonamento; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.abbonamento (id, tipo, datainizio, datafine, costomensile, cliente_cf) FROM stdin;
\.


--
-- TOC entry 5081 (class 0 OID 16463)
-- Dependencies: 225
-- Data for Name: accesso; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.accesso (id, datainizio, tipo, cliente_cf) FROM stdin;
\.


--
-- TOC entry 5076 (class 0 OID 16409)
-- Dependencies: 220
-- Data for Name: cliente; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.cliente (codicefiscale, nome, cognome, email, numerotelefonico, datanascita, dataiscrizione, password) FROM stdin;
\.


--
-- TOC entry 5079 (class 0 OID 16438)
-- Dependencies: 223
-- Data for Name: corso; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.corso (id, nome, capienzamax, orainizio, giorno) FROM stdin;
1	Yoga	20	16:00:00	MARTEDI
2	Pilates	25	17:00:00	GIOVEDI
3	Zumba	15	15:00:00	MERCOLEDI
4	Crossfit	5	12:00:00	SABATO
5	Pesi	30	18:00:00	VENERDI
6	Aerobica	5	11:00:00	LUNEDI
\.


--
-- TOC entry 5082 (class 0 OID 16489)
-- Dependencies: 226
-- Data for Name: iscrizione; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.iscrizione (id, dataiscrizione, stato, cliente_cf, corso_id) FROM stdin;
\.


--
-- TOC entry 5077 (class 0 OID 16416)
-- Dependencies: 221
-- Data for Name: istruttore; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.istruttore (codicefiscale, nome, cognome, email, numerotelefonico, datanascita, specializzazione, certificazioni, tariffaoraria, corso_id) FROM stdin;
BDDGTM63H26D332I	Gautama	Buddha	ilverobuddha@gmail.com	123 456 7890	0563-06-26	Yoga	Ascetismo estremo (Asceso al Nirvana)	\N	1
BLBRKY47L06B990R	Rocky	Balboa	tispiezzoindue@gmail.com	316 793 0019	1947-07-06	Pesi	Campione di pugilato	\N	5
DCCMRA90M29G702U	Mario	Doccia	pefozzaaaa@gmail.com	391 664 9315	1990-08-29	Pilates	Streamer dalle dubbie qualità	\N	2
GRCKTS44S11A662B	Kratos	Greco	godofwar@yahoo.com	1 800 613 8840	0400-03-03	Crossfit	Dio della Guerra a tempo perso	\N	4
MLNGRG77A15H501Y	Giorgio	Melone	giorgiomelone@gmail.com	346 521 5590	1977-01-15	Aerobica	Ex Presidente del Consiglio	\N	6
SLDSNK72A19I862C	Snake	Solid	keptyouwaiting.huh@gmail.com	141.80	1972-01-19	Zumba	Addetto alla demolizione di Metal Gear	\N	3
\.


--
-- TOC entry 5075 (class 0 OID 16389)
-- Dependencies: 219
-- Data for Name: persona; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.persona (codicefiscale, nome, cognome, email, numerotelefonico, datanascita) FROM stdin;
\.


--
-- TOC entry 5095 (class 0 OID 0)
-- Dependencies: 227
-- Name: abbonamento_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.abbonamento_id_seq', 1, false);


--
-- TOC entry 5096 (class 0 OID 0)
-- Dependencies: 228
-- Name: accesso_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.accesso_id_seq', 1, false);


--
-- TOC entry 5097 (class 0 OID 0)
-- Dependencies: 222
-- Name: corso_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.corso_id_seq', 6, true);


--
-- TOC entry 5098 (class 0 OID 0)
-- Dependencies: 229
-- Name: iscrizione_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.iscrizione_id_seq', 1, false);


--
-- TOC entry 4911 (class 2606 OID 16613)
-- Name: abbonamento abbonamento_cliente_cf_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.abbonamento
    ADD CONSTRAINT abbonamento_cliente_cf_key UNIQUE (cliente_cf);


--
-- TOC entry 4913 (class 2606 OID 16456)
-- Name: abbonamento abbonamento_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.abbonamento
    ADD CONSTRAINT abbonamento_pkey PRIMARY KEY (id);


--
-- TOC entry 4915 (class 2606 OID 16468)
-- Name: accesso accesso_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.accesso
    ADD CONSTRAINT accesso_pkey PRIMARY KEY (id);


--
-- TOC entry 4905 (class 2606 OID 16415)
-- Name: cliente cliente_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cliente
    ADD CONSTRAINT cliente_pkey PRIMARY KEY (codicefiscale);


--
-- TOC entry 4909 (class 2606 OID 16444)
-- Name: corso corso_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.corso
    ADD CONSTRAINT corso_pkey PRIMARY KEY (id);


--
-- TOC entry 4917 (class 2606 OID 16496)
-- Name: iscrizione iscrizione_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.iscrizione
    ADD CONSTRAINT iscrizione_pkey PRIMARY KEY (id);


--
-- TOC entry 4907 (class 2606 OID 16421)
-- Name: istruttore istruttore_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.istruttore
    ADD CONSTRAINT istruttore_pkey PRIMARY KEY (codicefiscale);


--
-- TOC entry 4903 (class 2606 OID 16394)
-- Name: persona persona_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.persona
    ADD CONSTRAINT persona_pkey PRIMARY KEY (codicefiscale);


--
-- TOC entry 4919 (class 2606 OID 16627)
-- Name: iscrizione uq_iscrizione_cliente_corso; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.iscrizione
    ADD CONSTRAINT uq_iscrizione_cliente_corso UNIQUE (cliente_cf, corso_id);


--
-- TOC entry 4925 (class 2620 OID 16642)
-- Name: abbonamento trg_applica_pricing_dinamico_gold; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER trg_applica_pricing_dinamico_gold BEFORE INSERT OR UPDATE OF tipo ON public.abbonamento FOR EACH ROW EXECUTE FUNCTION public.applica_pricing_dinamico_gold();


--
-- TOC entry 4927 (class 2620 OID 16640)
-- Name: iscrizione trg_gestisci_overbooking_iscrizione; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER trg_gestisci_overbooking_iscrizione BEFORE INSERT ON public.iscrizione FOR EACH ROW EXECUTE FUNCTION public.gestisci_overbooking_iscrizione();


--
-- TOC entry 4926 (class 2620 OID 16638)
-- Name: accesso trg_valida_accesso_abbonamento; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER trg_valida_accesso_abbonamento BEFORE INSERT ON public.accesso FOR EACH ROW EXECUTE FUNCTION public.valida_accesso_abbonamento();


--
-- TOC entry 4921 (class 2606 OID 16614)
-- Name: abbonamento fk_abbonamento_cliente; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.abbonamento
    ADD CONSTRAINT fk_abbonamento_cliente FOREIGN KEY (cliente_cf) REFERENCES public.cliente(codicefiscale);


--
-- TOC entry 4922 (class 2606 OID 16619)
-- Name: accesso fk_accesso_cliente; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.accesso
    ADD CONSTRAINT fk_accesso_cliente FOREIGN KEY (cliente_cf) REFERENCES public.cliente(codicefiscale);


--
-- TOC entry 4920 (class 2606 OID 16605)
-- Name: istruttore fk_istruttore_corso; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.istruttore
    ADD CONSTRAINT fk_istruttore_corso FOREIGN KEY (corso_id) REFERENCES public.corso(id);


--
-- TOC entry 4923 (class 2606 OID 16497)
-- Name: iscrizione iscrizione_cliente_cf_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.iscrizione
    ADD CONSTRAINT iscrizione_cliente_cf_fkey FOREIGN KEY (cliente_cf) REFERENCES public.cliente(codicefiscale);


--
-- TOC entry 4924 (class 2606 OID 16502)
-- Name: iscrizione iscrizione_corso_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.iscrizione
    ADD CONSTRAINT iscrizione_corso_id_fkey FOREIGN KEY (corso_id) REFERENCES public.corso(id);


-- Completed on 2026-06-10 15:55:32

--
-- PostgreSQL database dump complete
--

\unrestrict ghZhhMY1v1wehPnKPwqnSTvWEwLEfSA1vexfzZnrcYe6u1c324qya2w22SgG8r8

