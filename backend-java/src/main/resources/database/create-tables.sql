/*
Aqui fica todo o script de criação das tabelas do banco de dados, vindo primeiro
o script para SQL Server e depois o script para SQLite.
 */
/*
CREATE TABLE categorias (
  id INT PRIMARY KEY,
  nome VARCHAR(70) NOT NULL
)
 */
CREATE TABLE
  categorias (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nome TEXT NOT NULL
  );

/*
CREATE TABLE estados (
  id INT PRIMARY KEY,
  nome VARCHAR(70) NOT NULL,
  sigla CHAR(2) NOT NULL
)
 */
CREATE TABLE
  estados (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nome TEXT NOT NULL,
    sigla CHAR(2) NOT NULL
  );

/*
CREATE TABLE cidades (
  id INT PRIMARY KEY,
  nome VARCHAR(70) NOT NULL,
  id_estado INT,
  FOREIGN KEY (id_estado) REFERENCES estados(id_estado)
)
 */
CREATE TABLE
  cidades (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nome TEXT NOT NULL,
    id_estado INTEGER,
    FOREIGN KEY (id_estado) REFERENCES estados (id_estado)
  );

/*
CREATE TABLE fornecedores (
  id INT PRIMARY KEY,
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
    id INTEGER PRIMARY KEY AUTOINCREMENT,
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
  id INT PRIMARY KEY,
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
    id INTEGER PRIMARY KEY AUTOINCREMENT,
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
  id INT PRIMARY KEY,
  nome VARCHAR(70) NOT NULL,
  dt_validade DATE,
  preco DECIMAL(10, 2) NOT NULL,
  estoque INT NOT NULL,
  descricao VARCHAR(600),
  imagem VARCHAR(70),
  ativo TINYINT NOT NULL
)
 */
CREATE TABLE
  produtos (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nome TEXT NOT NULL,
    dt_validade DATETIME,
    preco DECIMAL(10, 2) NOT NULL,
    estoque INTEGER NOT NULL,
    descricao TEXT,
    imagem TEXT,
    ativo TINYINT NOT NULL
  );

/*
CREATE TABLE cupons (
  id INT PRIMARY KEY,
  nome VARCHAR(70) NOT NULL,
  porcentagem DECIMAL(5, 2) NOT NULL
)
 */
CREATE TABLE
  cupons (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nome TEXT NOT NULL,
    porcentagem DECIMAL(5, 2) NOT NULL
  );

/*
CREATE TABLE pedidos (
  id INT PRIMARY KEY,
  id_cliente INT,
  dt_pedido DATETIME NOT NULL,
  endereco VARCHAR(70) NOT NULL,
  status VARCHAR(50) NOT NULL,
  total DECIMAL(10, 2) NOT NULL,
  FOREIGN KEY (id_cliente) REFERENCES clientes(id_cliente)
)
 */
CREATE TABLE
  pedidos (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    id_cliente INTEGER,
    dt_pedido DATETIME NOT NULL,
    endereco TEXT NOT NULL,
    status TEXT NOT NULL,
    total DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (id_cliente) REFERENCES clientes (id_cliente)
  );

/*
CREATE TABLE avaliacoes (
  id INT PRIMARY KEY,
  id_cliente INT,
  id_produto INT,
  nota INT NOT NULL,
  comentario VARCHAR(600),
  dt_avaliacao DATETIME NOT NULL,
  FOREIGN KEY (id_cliente) REFERENCES clientes(id_cliente),
  FOREIGN KEY (id_produto) REFERENCES produtos(id_produto)
)
 */
CREATE TABLE
  avaliacoes (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    id_cliente INTEGER,
    id_produto INTEGER,
    nota INTEGER NOT NULL,
    comentario TEXT,
    dt_avaliacao DATETIME NOT NULL,
    FOREIGN KEY (id_cliente) REFERENCES clientes (id_cliente),
    FOREIGN KEY (id_produto) REFERENCES produtos (id_produto)
  );

/*
CREATE TABLE produtos_pedidos (
  id INT PRIMARY KEY,
  id_produto INT,
  id_pedido INT,
  preco DECIMAL(10, 2) NOT NULL,
  quantidade INT NOT NULL,
  FOREIGN KEY (id_produto) REFERENCES produtos(id_produto),
  FOREIGN KEY (id_pedido) REFERENCES pedidos(id_pedido)
)
 */
CREATE TABLE
  produtos_pedidos (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    id_produto INTEGER,
    id_pedido INTEGER,
    preco DECIMAL(10, 2) NOT NULL,
    quantidade INTEGER NOT NULL,
    FOREIGN KEY (id_produto) REFERENCES produtos (id_produto),
    FOREIGN KEY (id_pedido) REFERENCES pedidos (id_pedido)
  );

/*
CREATE TABLE produtos_fornecedores (
  id INT PRIMARY KEY,
  id_produto INT,
  id_fornecedore INT,
  preco DECIMAL(10, 2) NOT NULL,
  quantidade INT NOT NULL,
  data DATETIME NOT NULL,
  FOREIGN KEY (id_produto) REFERENCES produtos(id_produto),
  FOREIGN KEY (id_fornecedor) REFERENCES fornecedores(id_fornecedor)
)
 */
CREATE TABLE
  produtos_fornecedores (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    id_produto INTEGER,
    id_fornecedor INTEGER,
    preco DECIMAL(10, 2) NOT NULL,
    quantidade INTEGER NOT NULL,
    data DATETIME NOT NULL,
    FOREIGN KEY (id_produto) REFERENCES produtos (id_produto),
    FOREIGN KEY (id_fornecedor) REFERENCES fornecedores (id_fornecedor)
  );

/*
CREATE TABLE clientes_cupons (
  id INT PRIMARY KEY,
  id_cupom INT,
  id_cliente INT,
  data_utilizacao DATETIME NOT NULL,
  FOREIGN KEY (id_cupom) REFERENCES cupons(id_cupom),
  FOREIGN KEY (id_cliente) REFERENCES clientes(id_cliente)
)
 */
CREATE TABLE
  clientes_cupons (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    id_cupom INTEGER,
    id_cliente INTEGER,
    data_utilizacao DATETIME NOT NULL,
    FOREIGN KEY (id_cupom) REFERENCES cupons (id_cupom),
    FOREIGN KEY (id_cliente) REFERENCES clientes (id_cliente)
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
  FOREIGN KEY (id_produto) REFERENCES produtos(id_produto),
  FOREIGN KEY (id_categoria) REFERENCES categorias(id_categoria)
)
 */
create table produtos_categorias (
  id INTEGER PRIMARY KEY AUTOINCREMENT,
  id_produto INTEGER,
  id_categoria INTEGER,
  FOREIGN KEY (id_produto) REFERENCES produtos (id_produto),
  FOREIGN KEY (id_categoria) REFERENCES categorias (id_categoria)
)