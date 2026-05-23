
CREATE TABLE usuario (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    endereco VARCHAR(200),
    dtype VARCHAR(31) NOT NULL
);

CREATE TABLE motorista (
    id BIGINT PRIMARY KEY,
    habilitado BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (id) REFERENCES usuario(id)
);

CREATE TABLE carona (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    origem VARCHAR(100) NOT NULL,
    destino VARCHAR(100) NOT NULL,
    data_hora DATETIME NOT NULL,
    vagas_disponiveis INT NOT NULL,
    motorista_id BIGINT NOT NULL,
    FOREIGN KEY (motorista_id) REFERENCES usuario(id)
);

CREATE TABLE solicitacao (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    passageiro_id BIGINT NOT NULL,
    carona_id BIGINT NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'pendente',
    FOREIGN KEY (passageiro_id) REFERENCES usuario(id),
    FOREIGN KEY (carona_id) REFERENCES carona(id)
);