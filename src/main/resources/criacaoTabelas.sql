CREATE TABLE tipo_medicamento (
    tpm_id SERIAL PRIMARY KEY,
    tpm_nome VARCHAR(255) NOT NULL,
    tpm_forma VARCHAR(255) NOT NULL,
    tpm_descricao VARCHAR(255) NOT NULL
);

CREATE TABLE tipo_lancamento (
    tpl_id SERIAL PRIMARY KEY,
    tpl_descricao VARCHAR(255) NOT NULL
);

CREATE TABLE plano_contas_referencial (
    pcr_id SERIAL PRIMARY KEY,
    pcr_descricao VARCHAR(255) NOT NULL,
    pcr_natureza VARCHAR(10),
    pcr_classificacao VARCHAR(30) NOT NULL
);

CREATE TABLE plano_contas_gerencial (
    pcg_id SERIAL PRIMARY KEY,
    pcg_descricao VARCHAR(255) NOT NULL,
    pcr_id INTEGER NOT NULL,
    FOREIGN KEY (pcr_id) REFERENCES plano_contas_referencial(pcr_id)
);

CREATE TABLE animal (
    ani_id SERIAL,
    ani_nome VARCHAR(255) NOT NULL,
    ani_sexo VARCHAR(10) NOT NULL,
    ani_raca VARCHAR(255) NOT NULL,
    ani_peso DECIMAL(5, 2) NOT NULL CHECK (ani_peso > 0),
    ani_castrado VARCHAR(10) NOT NULL,
    ani_adotado VARCHAR(10) NOT NULL,
    ani_imagem TEXT NOT NULL,
    ani_dtnasc DATE NOT NULL,
    ani_cor VARCHAR(255) NOT NULL,
    ani_especie VARCHAR(255) NOT NULL,
    CONSTRAINT pk_animal PRIMARY KEY (ani_id)
);

CREATE TABLE usuario (
    usu_id SERIAL PRIMARY KEY,
    usu_nome VARCHAR(255) NOT NULL,
    usu_email VARCHAR(255) NOT NULL,
    usu_senha VARCHAR(255) NOT NULL,
    usu_telefone VARCHAR(20) NOT NULL,
    usu_cpf VARCHAR(14) NOT NULL,
    usu_privilegio VARCHAR(50) NOT NULL,
    usu_sexo VARCHAR(10) NOT NULL,
    usu_cep VARCHAR(10) NOT NULL,
    usu_rua VARCHAR(255) NOT NULL,
    usu_bairro VARCHAR(255) NOT NULL,
    usu_numero VARCHAR(10) NOT NULL,
    usu_cidade VARCHAR(20) NOT NULL,
    usu_estado VARCHAR(15) NOT NULL
);

CREATE TABLE agendar_medicamento (
    agemed_id SERIAL PRIMARY KEY,
    agemed_medicamento_id INTEGER NOT NULL,
    agemed_animal_id INTEGER NOT NULL,
    agemed_dataAplicacao DATE NOT NULL,
    agemed_status BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (agemed_medicamento_id) REFERENCES tipo_medicamento(tpm_id),
    FOREIGN KEY (agemed_animal_id) REFERENCES animal(ani_id)
);

CREATE TABLE adocao (
    ado_id SERIAL PRIMARY KEY,
    ado_animal_id INTEGER NOT NULL,
    ado_usuario_id INTEGER NOT NULL,
    ado_data DATE NOT NULL,
    ado_status VARCHAR(50) NOT NULL,
    FOREIGN KEY (ado_animal_id) REFERENCES animal(ani_id),
    FOREIGN KEY (ado_usuario_id) REFERENCES usuario(usu_id)
);

CREATE TABLE lancamento (
    lan_id SERIAL PRIMARY KEY,
    lan_codTpLanc INTEGER NOT NULL,
    lan_codAnimal INTEGER,
    lan_data DATE NOT NULL,
    lan_debito INTEGER NOT NULL,
    lan_credito INTEGER NOT NULL,
    lan_descricao VARCHAR(255),
    lan_valor NUMERIC,
    lan_pdf BYTEA,
    FOREIGN KEY (lan_codTpLanc) REFERENCES tipo_lancamento(tpl_id),
    FOREIGN KEY (lan_codAnimal) REFERENCES animal(ani_id),
    FOREIGN KEY (lan_debito) REFERENCES plano_contas_gerencial(pcg_id),
    FOREIGN KEY (lan_credito) REFERENCES plano_contas_gerencial(pcg_id)
);

CREATE TABLE doacao(
   doa_id SERIAL PRIMARY KEY,
   doa_usuario_id INTEGER NOT NULL,
   doa_status VARCHAR(50) NOT NULL,
   doa_valor INTEGER NOT NULL,
   doa_data DATE NOT NULL,
   FOREIGN KEY (doa_usuario_id) REFERENCES usuario(usu_id)
);

CREATE TABLE prontuario(
    pron_id SERIAL PRIMARY KEY,
    pron_animal_id INTEGER NOT NULL,
    pron_data DATE NOT NULL,
    pron_tipoRegistro VARCHAR(50) NOT NULL,
    pron_observacao VARCHAR(255),
    pron_documento BYTEA,
    FOREIGN KEY (pron_animal_id) REFERENCES animal(ani_id)
);
