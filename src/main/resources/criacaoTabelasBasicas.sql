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
CREATE TABLE animal (
    ani_id SERIAL PRIMARY KEY,
    ani_nome VARCHAR(255) NOT NULL,
    ani_sexo VARCHAR(10) NOT NULL,
    ani_raca VARCHAR(255) NOT NULL,
    ani_idade INT NOT NULL CHECK (ani_idade > 0),
    ani_peso DECIMAL(5, 2) NOT NULL CHECK (ani_peso > 0),
    ani_castrado VARCHAR(10) NOT NULL,
    ani_adotado VARCHAR(10) NOT NULL,
    ani_imagem TEXT NOT NULL
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
    usu_numero VARCHAR(10) NOT NULL
);

CREATE TABLE agendar_medicamento (
    agemed_id SERIAL PRIMARY KEY,
    agemed_medicamento_id INTEGER NOT NULL,
    agemed_animal_id INTEGER NOT NULL,
    agemed_intervalo INTEGER NOT NULL, -- de quanto em quanto tempo
    agemed_formato VARCHAR(10) NOT NULL,-- dia ou hora
    agemed_periodo INTEGER NOT NULL, --durante quanto tempo
    FOREIGN KEY (agemed_medicamento_id) REFERENCES tipo_medicamento(tpm_id),
    FOREIGN KEY (agemed_animal_id) REFERENCES animal(ani_id)
);

