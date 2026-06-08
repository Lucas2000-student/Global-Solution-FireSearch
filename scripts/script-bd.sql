-- =============================================================================
-- DDL Oficial - Projeto S.E.N.T.I.N.E.L.A.
-- Plataforma de Monitoramento de Desastres Naturais com Dados Espaciais
-- FIAP — ADS 2026/1
-- =============================================================================

-- =============================================================================
-- TABELAS
-- =============================================================================

CREATE TABLE t_sen_regiao (
       id_regiao      NUMBER NOT NULL,
       nm_regiao      VARCHAR2(100) NOT NULL,
       nm_estado      VARCHAR2(50) NOT NULL,
       nm_pais        VARCHAR2(50) NOT NULL,
       re_latitude    NUMBER(10, 6) NOT NULL,
       re_longitude   NUMBER(10, 6) NOT NULL,
       CONSTRAINT pk_t_sen_regiao PRIMARY KEY (id_regiao)
);

CREATE TABLE t_sen_satelite (
       id_satelite    NUMBER NOT NULL,
       nm_satelite    VARCHAR2(50) NOT NULL,
       ds_fonte       VARCHAR2(50) NOT NULL,
       ds_descricao   VARCHAR2(200) NOT NULL,
       CONSTRAINT pk_t_sen_satelite PRIMARY KEY (id_satelite)
);

CREATE TABLE t_sen_usuario (
       id_usuario       NUMBER NOT NULL,
       nome             VARCHAR2(100) NOT NULL,
       email            VARCHAR2(150) NOT NULL,
       senha_hash       VARCHAR2(150) NOT NULL,
       fcm_token        VARCHAR2(500) NOT NULL,
       latitude         NUMBER(10, 6),
       longitude        NUMBER(10, 6),
       raio_km          NUMBER(5),
       data_cadastro    DATE NOT NULL,
       ativo            CHAR(1) DEFAULT 'S' NOT NULL,
       CONSTRAINT pk_t_sen_usuario PRIMARY KEY (id_usuario)
);

CREATE TABLE t_sen_usuario_regiao (
       t_sen_usuario_id_usuario    NUMBER NOT NULL,
       t_sen_regiao_id_regiao      NUMBER NOT NULL,
       data_inscricao              DATE NOT NULL,
       us_re_ativo                 CHAR(1) DEFAULT 'S' NOT NULL,
       CONSTRAINT pk_t_sen_usuario_regiao PRIMARY KEY (t_sen_usuario_id_usuario, t_sen_regiao_id_regiao),
       CONSTRAINT fk_usreg_usuario FOREIGN KEY (t_sen_usuario_id_usuario) REFERENCES t_sen_usuario(id_usuario),
       CONSTRAINT fk_usreg_regiao  FOREIGN KEY (t_sen_regiao_id_regiao)   REFERENCES t_sen_regiao(id_regiao)
);

CREATE TABLE t_sen_foco_calor (
       id_foco                       NUMBER NOT NULL,
       fc_latitude                   NUMBER(10, 6) NOT NULL,
       fc_longitude                  NUMBER(10, 6) NOT NULL,
       brightness                    NUMBER(8, 2),
       frp                           NUMBER(8, 2),
       confidence                    VARCHAR2(20),
       dt_aquisicao                  DATE NOT NULL,
       hr_aquisicao                  VARCHAR2(10),
       nm_satelite_origem            VARCHAR2(50),
       data_importacao               DATE NOT NULL,
       t_sen_satelite_id_satelite    NUMBER NOT NULL,
       t_sen_regiao_id_regiao        NUMBER NOT NULL,
       CONSTRAINT pk_t_sen_foco_calor PRIMARY KEY (id_foco),
       CONSTRAINT fk_foco_satelite FOREIGN KEY (t_sen_satelite_id_satelite) REFERENCES t_sen_satelite(id_satelite),
       CONSTRAINT fk_foco_regiao   FOREIGN KEY (t_sen_regiao_id_regiao)     REFERENCES t_sen_regiao(id_regiao)
);

CREATE TABLE t_sen_historico_risco (
       id_historico              NUMBER NOT NULL,
       score                     NUMBER(5, 2) NOT NULL,
       ds_nivel                  VARCHAR2(100) NOT NULL,
       data_calculo              DATE NOT NULL,
       t_sen_regiao_id_regiao    NUMBER NOT NULL,
       CONSTRAINT pk_t_sen_historico_risco PRIMARY KEY (id_historico),
       CONSTRAINT fk_historico_regiao FOREIGN KEY (t_sen_regiao_id_regiao) REFERENCES t_sen_regiao(id_regiao)
);

CREATE TABLE t_sen_alerta (
       id_alerta                              NUMBER NOT NULL,
       ds_nivel                               VARCHAR2(100) NOT NULL,
       ds_mensagem                            VARCHAR2(500) NOT NULL,
       data_emissao                           DATE NOT NULL,
       fl_notificado                          CHAR(1) DEFAULT 'N' NOT NULL,
       t_sen_historico_risco_id_historico     NUMBER NOT NULL,
       CONSTRAINT pk_t_sen_alerta PRIMARY KEY (id_alerta),
                              CONSTRAINT fk_alerta_historico FOREIGN KEY (t_sen_historico_risco_id_historico) REFERENCES t_sen_historico_risco(id_historico)
);

CREATE TABLE t_sen_notificacao (
       id_notificacao             NUMBER NOT NULL,
       data_envio                 DATE NOT NULL,
       fl_entregue                CHAR(1) DEFAULT 'N' NOT NULL,
       t_sen_alerta_id_alerta     NUMBER NOT NULL,
       t_sen_usuario_id_usuario   NUMBER NOT NULL,
       CONSTRAINT pk_t_sen_notificacao PRIMARY KEY (id_notificacao),
       CONSTRAINT fk_notif_alerta  FOREIGN KEY (t_sen_alerta_id_alerta)   REFERENCES t_sen_alerta(id_alerta),
       CONSTRAINT fk_notif_usuario FOREIGN KEY (t_sen_usuario_id_usuario) REFERENCES t_sen_usuario(id_usuario)
);

-- =============================================================================
-- SEQUENCES
-- =============================================================================

CREATE SEQUENCE seq_regiao          START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE SEQUENCE seq_satelite        START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE SEQUENCE seq_usuario         START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE SEQUENCE seq_foco_calor      START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE SEQUENCE seq_historico_risco START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE SEQUENCE seq_alerta          START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE SEQUENCE seq_notificacao     START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;

-- =============================================================================
-- INSERT OBRIGATÓRIO — Satélite NASA FIRMS
-- Necessário para o fluxo de importação automática da API NASA FIRMS
-- =============================================================================

INSERT INTO t_sen_satelite (id_satelite, nm_satelite, ds_fonte, ds_descricao)
VALUES (seq_satelite.NEXTVAL, 'VIIRS SNPP', 'VIIRS_SNPP_NRT',
        'Satélite Suomi NPP com sensor VIIRS usado pela NASA FIRMS para detecção de focos em tempo real');

COMMIT;