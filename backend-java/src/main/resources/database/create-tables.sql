/*
Aqui fica todo o script de criação das tabelas do banco de dados, vindo primeiro
o script para SQL Server e depois o script para SQLite.
 */
/*
CREATE TABLE categoria (
  id_categoria INT PRIMARY KEY,
  nome VARCHAR(70) NOT NULL
)
 */
CREATE TABLE
  categoria (
    id_categoria INTEGER PRIMARY KEY AUTOINCREMENT,
    nome TEXT NOT NULL
  );

/*
CREATE TABLE estados (
  id_estado INT PRIMARY KEY,
  nome VARCHAR(70) NOT NULL,
  sigla CHAR(2) NOT NULL
)
 */
CREATE TABLE
  estados (
    id_estado INTEGER PRIMARY KEY AUTOINCREMENT,
    nome TEXT NOT NULL,
    sigla CHAR(2) NOT NULL
  );

/*
CREATE TABLE cidades (
  id_cidade INT PRIMARY KEY,
  nome VARCHAR(70) NOT NULL,
  id_estado INT,
  FOREIGN KEY (id_estado) REFERENCES estados(id_estado)
)
 */
CREATE TABLE
  cidades (
    id_cidade INTEGER PRIMARY KEY AUTOINCREMENT,
    nome TEXT NOT NULL,
    id_estado INTEGER,
    FOREIGN KEY (id_estado) REFERENCES estados (id_estado)
  );

/*
CREATE TABLE fornecedores (
  id_fornecedores INT PRIMARY KEY,
  nome VARCHAR(70) NOT NULL,
  id_cidade INT,
  cep VARCHAR(10) NOT NULL,
  endereco VARCHAR(70) NOT NULL,
  complemento VARCHAR(70),
  telefone VARCHAR(15) NOT NULL,
  status VARCHAR(50) NOT NULL,
  FOREIGN KEY (id_cidade) REFERENCES cidades(id_cidade)
)
 */
CREATE TABLE
  fornecedores (
    id_fornecedores INTEGER PRIMARY KEY AUTOINCREMENT,
    nome TEXT NOT NULL,
    id_cidade INTEGER,
    cep TEXT NOT NULL,
    endereco TEXT NOT NULL,
    complemento TEXT,
    telefone TEXT NOT NULL,
    status TEXT NOT NULL,
    FOREIGN KEY (id_cidade) REFERENCES cidades (id_cidade)
  );

/*
CREATE TABLE clientes (
  id_clientes INT PRIMARY KEY,
  nome VARCHAR(70) NOT NULL,
  id_cidade INT,
  cep VARCHAR(10) NOT NULL,
  endereco VARCHAR(70) NOT NULL,
  email VARCHAR(70) NOT NULL,
  telefone VARCHAR(15) NOT NULL,
  bairro VARCHAR(70) NOT NULL,
  numero VARCHAR(6) NOT NULL,
  senha VARCHAR(70) NOT NULL,
  FOREIGN KEY (id_cidade) REFERENCES cidades(id_cidade)
)
 */
CREATE TABLE
  clientes (
    id_clientes INTEGER PRIMARY KEY AUTOINCREMENT,
    nome TEXT NOT NULL,
    id_cidade INTEGER,
    cep TEXT NOT NULL,
    endereco TEXT NOT NULL,
    telefone TEXT NOT NULL,
    bairro TEXT NOT NULL,
    numero TEXT NOT NULL,
    email TEXT NOT NULL,
    senha TEXT NOT NULL,
    FOREIGN KEY (id_cidade) REFERENCES cidades (id_cidade)
  );

/*
CREATE TABLE produtos (
  id_produtos INT PRIMARY KEY,
  nome VARCHAR(70) NOT NULL,
  dt_validade DATE,
  preco DECIMAL(10, 2) NOT NULL,
  estoque INT NOT NULL,
  descricao VARCHAR(600),
  imagem VARCHAR(70)
)
 */
CREATE TABLE
  produtos (
    id_produtos INTEGER PRIMARY KEY AUTOINCREMENT,
    nome TEXT NOT NULL,
    dt_validade DATETIME,
    preco DECIMAL(10, 2) NOT NULL,
    estoque INTEGER NOT NULL,
    descricao TEXT,
    imagem TEXT
  );

/*
CREATE TABLE cupons (
  id_cupons INT PRIMARY KEY,
  nome VARCHAR(70) NOT NULL,
  porcentagem DECIMAL(5, 2) NOT NULL
)
 */
CREATE TABLE
  cupons (
    id_cupons INTEGER PRIMARY KEY AUTOINCREMENT,
    nome TEXT NOT NULL,
    porcentagem DECIMAL(5, 2) NOT NULL
  );

/*
CREATE TABLE pedidos (
  id_pedidos INT PRIMARY KEY,
  id_clientes INT,
  dt_pedido DATETIME NOT NULL,
  endereco VARCHAR(70) NOT NULL,
  status VARCHAR(50) NOT NULL,
  total DECIMAL(10, 2) NOT NULL,
  FOREIGN KEY (id_clientes) REFERENCES clientes(id_clientes)
)
 */
CREATE TABLE
  pedidos (
    id_pedidos INTEGER PRIMARY KEY AUTOINCREMENT,
    id_clientes INTEGER,
    dt_pedido DATETIME NOT NULL,
    endereco TEXT NOT NULL,
    status TEXT NOT NULL,
    total DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (id_clientes) REFERENCES clientes (id_clientes)
  );

/*
CREATE TABLE avaliacoes (
  id_avaliacoes INT PRIMARY KEY,
  id_clientes INT,
  id_produtos INT,
  nota INT NOT NULL,
  comentario VARCHAR(600),
  dt_avaliacao DATETIME NOT NULL,
  FOREIGN KEY (id_clientes) REFERENCES clientes(id_clientes),
  FOREIGN KEY (id_produtos) REFERENCES produtos(id_produtos)
)
 */
CREATE TABLE
  avaliacoes (
    id_avaliacoes INTEGER PRIMARY KEY AUTOINCREMENT,
    id_clientes INTEGER,
    id_produtos INTEGER,
    nota INTEGER NOT NULL,
    comentario TEXT,
    dt_avaliacao DATETIME NOT NULL,
    FOREIGN KEY (id_clientes) REFERENCES clientes (id_clientes),
    FOREIGN KEY (id_produtos) REFERENCES produtos (id_produtos)
  );

/*
CREATE TABLE produtos_pedidos (
  id INT PRIMARY KEY,
  id_produtos INT,
  id_pedidos INT,
  preco DECIMAL(10, 2) NOT NULL,
  quantidade INT NOT NULL,
  FOREIGN KEY (id_produtos) REFERENCES produtos(id_produtos),
  FOREIGN KEY (id_pedidos) REFERENCES pedidos(id_pedidos)
)
 */
CREATE TABLE
  produtos_pedidos (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    id_produtos INTEGER,
    id_pedidos INTEGER,
    preco DECIMAL(10, 2) NOT NULL,
    quantidade INTEGER NOT NULL,
    FOREIGN KEY (id_produtos) REFERENCES produtos (id_produtos),
    FOREIGN KEY (id_pedidos) REFERENCES pedidos (id_pedidos)
  );

/*
CREATE TABLE produtos_fornecedores (
  id INT PRIMARY KEY,
  id_produtos INT,
  id_fornecedores INT,
  preco DECIMAL(10, 2) NOT NULL,
  quantidade INT NOT NULL,
  data DATETIME NOT NULL,
  FOREIGN KEY (id_produtos) REFERENCES produtos(id_produtos),
  FOREIGN KEY (id_fornecedores) REFERENCES fornecedores(id_fornecedores)
)
 */
CREATE TABLE
  produtos_fornecedores (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    id_produtos INTEGER,
    id_fornecedores INTEGER,
    preco DECIMAL(10, 2) NOT NULL,
    quantidade INTEGER NOT NULL,
    data DATETIME NOT NULL,
    FOREIGN KEY (id_produtos) REFERENCES produtos (id_produtos),
    FOREIGN KEY (id_fornecedores) REFERENCES fornecedores (id_fornecedores)
  );

/*
CREATE TABLE clientes_cupons (
  id INT PRIMARY KEY,
  id_cupons INT,
  id_clientes INT,
  data_utilizacao DATETIME NOT NULL,
  FOREIGN KEY (id_cupons) REFERENCES cupons(id_cupons),
  FOREIGN KEY (id_clientes) REFERENCES clientes(id_clientes)
)
 */
CREATE TABLE
  clientes_cupons (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    id_cupons INTEGER,
    id_clientes INTEGER,
    data_utilizacao DATETIME NOT NULL,
    FOREIGN KEY (id_cupons) REFERENCES cupons (id_cupons),
    FOREIGN KEY (id_clientes) REFERENCES clientes (id_clientes)
  );

/*
CREATE TABLE usuarios (
  id INT PRIMARY KEY,
  nome VARCHAR(70) NOT NULL,
  email VARCHAR(70) NOT NULL,
  senha VARCHAR(70) NOT NULL,
)
 */
CREATE TABLE
  usuarios (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nome TEXT NOT NULL,
    email TEXT NOT NULL,
    senha TEXT NOT NULL
  );

/*
CREATE TABLE produtos_categorias (
  id INT PRIMARY KEY,
  id_produto INT,
  id_categoria INT,
  FOREIGN KEY (id_produtos) REFERENCES produtos(id_produtos),
  FOREIGN KEY (id_categoria) REFERENCES categoria(id_categoria)
)
 */
create table produtos_categorias (
  id INTEGER PRIMARY KEY AUTOINCREMENT,
  id_produto INTEGER,
  id_categoria INTEGER,
  FOREIGN KEY (id_produto) REFERENCES produtos (id_produtos),
  FOREIGN KEY (id_categoria) REFERENCES categoria (id_categoria)
)