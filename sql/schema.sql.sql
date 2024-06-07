CREATE TABLE public.availability (
    id integer NOT NULL,
    event_id integer NOT NULL,
    user_id integer NOT NULL,
    available boolean
);

CREATE TABLE public.event_groups (
    id integer NOT NULL,
    name character varying(255) NOT NULL,
    availability_active boolean DEFAULT false,
    active boolean DEFAULT false
);

CREATE TABLE public.event_position_assignments (
    id bigint NOT NULL,
    event_id bigint,
    position_id bigint,
    user_id bigint
);

CREATE TABLE public.events (
    id integer NOT NULL,
    show_id integer NOT NULL,
    event_start_time timestamp without time zone NOT NULL,
    event_group_id integer
);

CREATE TABLE public.payrolls (
    id integer NOT NULL,
    user_id integer NOT NULL,
    date date NOT NULL,
    start_time time without time zone NOT NULL,
    end_time time without time zone NOT NULL,
    hours double precision NOT NULL
);

CREATE TABLE public.positions (
    id integer NOT NULL,
    name character varying(255) NOT NULL,
    position_type character varying(255) NOT NULL,
    quantity integer NOT NULL,
    stage_id integer
);

CREATE TABLE public.shows (
    id integer NOT NULL,
    title character varying(255) NOT NULL,
    duration integer NOT NULL,
    stage_id integer,
    additional_information text
);

CREATE TABLE public.stages (
    id integer NOT NULL,
    name character varying(255),
    number_of_seats integer,
    address character varying(255)
);

CREATE TABLE public.users (
    id integer NOT NULL,
    email character varying(255),
    first_name character varying(255),
    last_name character varying(255),
    phone_number character varying(255),
    password character varying(255) DEFAULT 'capitol'::character varying NOT NULL,
    role character varying(50) DEFAULT 'EMPLOYEE'::character varying NOT NULL
);

ALTER TABLE ONLY public.availability
    ADD CONSTRAINT availability_pkey PRIMARY KEY (id);


--
-- TOC entry 3239 (class 2606 OID 45104)
-- Name: event_groups event_groups_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.event_groups
    ADD CONSTRAINT event_groups_pkey PRIMARY KEY (id);


--
-- TOC entry 3241 (class 2606 OID 45176)
-- Name: event_position_assignments event_position_assignments_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.event_position_assignments
    ADD CONSTRAINT event_position_assignments_pkey PRIMARY KEY (id);


--
-- TOC entry 3235 (class 2606 OID 45025)
-- Name: events events_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.events
    ADD CONSTRAINT events_pkey PRIMARY KEY (id);


--
-- TOC entry 3243 (class 2606 OID 45210)
-- Name: payrolls payrolls_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.payrolls
    ADD CONSTRAINT payrolls_pkey PRIMARY KEY (id);


--
-- TOC entry 3229 (class 2606 OID 44940)
-- Name: positions positions_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.positions
    ADD CONSTRAINT positions_pkey PRIMARY KEY (id);


--
-- TOC entry 3233 (class 2606 OID 45009)
-- Name: shows shows_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.shows
    ADD CONSTRAINT shows_pkey PRIMARY KEY (id);


--
-- TOC entry 3231 (class 2606 OID 44949)
-- Name: stages stages_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.stages
    ADD CONSTRAINT stages_pkey PRIMARY KEY (id);


--
-- TOC entry 3227 (class 2606 OID 36749)
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- TOC entry 3250 (class 2606 OID 45177)
-- Name: event_position_assignments event_position_assignments_event_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.event_position_assignments
    ADD CONSTRAINT event_position_assignments_event_id_fkey FOREIGN KEY (event_id) REFERENCES public.events(id);


--
-- TOC entry 3251 (class 2606 OID 45182)
-- Name: event_position_assignments event_position_assignments_position_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.event_position_assignments
    ADD CONSTRAINT event_position_assignments_position_id_fkey FOREIGN KEY (position_id) REFERENCES public.positions(id);


--
-- TOC entry 3252 (class 2606 OID 45187)
-- Name: event_position_assignments event_position_assignments_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.event_position_assignments
    ADD CONSTRAINT event_position_assignments_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- TOC entry 3247 (class 2606 OID 45162)
-- Name: events events_event_group_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.events
    ADD CONSTRAINT events_event_group_id_fkey FOREIGN KEY (event_group_id) REFERENCES public.event_groups(id);


--
-- TOC entry 3248 (class 2606 OID 45072)
-- Name: availability fk_event; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.availability
    ADD CONSTRAINT fk_event FOREIGN KEY (event_id) REFERENCES public.events(id);


--
-- TOC entry 3246 (class 2606 OID 45026)
-- Name: events fk_show; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.events
    ADD CONSTRAINT fk_show FOREIGN KEY (show_id) REFERENCES public.shows(id);


--
-- TOC entry 3249 (class 2606 OID 45077)
-- Name: availability fk_user; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.availability
    ADD CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- TOC entry 3253 (class 2606 OID 45211)
-- Name: payrolls payrolls_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.payrolls
    ADD CONSTRAINT payrolls_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- TOC entry 3244 (class 2606 OID 45216)
-- Name: positions positions_stage_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.positions
    ADD CONSTRAINT positions_stage_id_fkey FOREIGN KEY (stage_id) REFERENCES public.stages(id);


--
-- TOC entry 3245 (class 2606 OID 45010)
-- Name: shows shows_stage_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.shows
    ADD CONSTRAINT shows_stage_id_fkey FOREIGN KEY (stage_id) REFERENCES public.stages(id);


-- Completed on 2024-06-07 13:00:47 CEST

--
-- PostgreSQL database dump complete
--

