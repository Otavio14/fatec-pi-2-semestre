CREATE TABLE clientes (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    id_cidades INTEGER NOT NULL,
    nome TEXT NOT NULL,
    cep TEXT NOT NULL,
    email TEXT NOT NULL,
    telefone TEXT NOT NULL,
    bairro TEXT NOT NULL,
    numero INTEGER NOT NULL,
    endereco TEXT NOT NULL,
    FOREIGN KEY (id_cidades) REFERENCES cidades (id)
);