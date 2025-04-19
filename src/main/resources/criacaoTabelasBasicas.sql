CREATE TABLE tipo_medicamento (
      tpm_id SERIAL,
      tpm_nome VARCHAR(255) NOT NULL,
      tpm_forma VARCHAR(255) NOT NULL,
      tpm_descricao VARCHAR(255) NOT NULL,
      CONSTRAINT pk_tipo_medicamento PRIMARY KEY (tpm_id)
);

CREATE TABLE tipo_lancamento (
     tpl_id SERIAL,
     tpl_descricao VARCHAR(255) NOT NULL,
     CONSTRAINT pk_tipo_lancamento PRIMARY KEY (tpl_id)
);

CREATE TABLE animal (
    ani_id SERIAL,
    ani_nome VARCHAR(255) NOT NULL,
    ani_sexo VARCHAR(10) NOT NULL,
    ani_raca VARCHAR(255) NOT NULL,
    ani_idade INT NOT NULL CHECK (ani_idade > 0),
    ani_peso DECIMAL(5, 2) NOT NULL CHECK (ani_peso > 0),
    ani_castrado VARCHAR(10) NOT NULL,
    ani_adotado VARCHAR(10) NOT NULL,
    ani_imagem TEXT NOT NULL,
    CONSTRAINT pk_animal PRIMARY KEY (ani_id)
);

CREATE TABLE usuario (
     usu_id SERIAL,
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
     CONSTRAINT pk_usuario PRIMARY KEY (usu_id)
);

CREATE TABLE adocao (
    ado_id SERIAL,
    ado_animal_id INTEGER NOT NULL,
    ado_usuario_id INTEGER NOT NULL,
    ado_data DATE NOT NULL,
    ado_status VARCHAR(50) NOT NULL,
    CONSTRAINT pk_adocao PRIMARY KEY (ado_id),
    CONSTRAINT fk_adocao_animal FOREIGN KEY (ado_animal_id) REFERENCES animal(ani_id),
    CONSTRAINT fk_adocao_usuario FOREIGN KEY (ado_usuario_id) REFERENCES usuario(usu_id)
);
