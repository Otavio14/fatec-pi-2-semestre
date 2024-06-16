import swaggerAutogen from 'swagger-autogen'

const doc = {
    info: {
        version: '1.0.0',
        title: 'API principal',
        description: 'API do projeto integrador'
    },
    servers: [
        { url: 'http://localhost:3000' },
        { url: 'http://render.com'}
    ],
    definitions: {
        AddOrUpdateUser: {
            nome:"User 1", email:"user@gmail.com", senha:"abcdfg"  // Usuários
        },
        AddOrUpdateAvaliacao: { 
            id_produtos: 1, id_clientes: 1, nota: "5", comentario:"Muito Bom", dt_avaliacao: "13/04/2024" // Avaliações
        },
        AddOrUpdateCategoria:{
            nome:"Categoria 1" //Categorias
        },
        AddOrUpdateCidade:{
            nome:"Cidade 1", id_estados: 1 // Cidades
        },
        AddOrUpdateCliente:{
            id_cidades: 1, nome:"Cliente 1", cep:"13350000", email:"cliente@gmail.com", telefone:"19999999999", bairro:"Jardins",numero:"136", endereco:"Rua Tal e Tal" //Clientes
        },
        AddOrUpdateCupom:{
            nome:"Cupom 1", porcentagem: 8 //Cupons
        },
        AddOrUpdateEstado:{
            nome:"São Paulo", sigla:"SP" // Estados
        },
        AddOrUpdateFornecedor:{
            nome:"Fornecedor 1", endereco:"Rua Tal e Tão", cep:"13600000", complemento:"Alguma Coisa", telefone:"19988998899", status:"",id_cidades: 1 // Fornecedores
        },
        AddOrUpdateFornecedor_Produto:{
            id_fornecedores: 1, id_produtos: 1, preco:"300,30", quantidade:"9" // Fornecedores_Produtos
        },  
        AddOrUpdatePedido:{
            id_clientes: 1, status:"", endereco:"Rua Tal Tal", dt_pedido:"13/04/2024", total:"300,30" // Pedidos
        },
        AddOrUpdateProduto:{
            nome:"Produto 1", preco:"300,30", estoque:"9", descricao:"Alguma Coisa", dt_validade:"25/09/2024", imagem:"url" // Produtos
        },
        AddOrUpdateProduto_Categoria:{
            id_produtos: 1, id_categorias: 1 // Produtos_Categorias
        },
        AddOrUpdateProduto_Pedido:{
            id_produtos: 1, id_pedidos: 1, quantidade:"1", preco:"300,30" // Produtos_Pedidos
        },
    }
}

const outputFile = './swagger-output.json'
const endpoints = ['./server.js']

swaggerAutogen({ openapi: '3.0.0'})(outputFile,endpoints,doc)