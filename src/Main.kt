data class Produto (
    var nome: String,
    var descricao: String,
    var preco: Float,
    var estoque: Int,
    var codigo: Int
)

data class Pedido (
    var numeroPedido: Int,
    var itens: MutableList<Produto>,
    var total: Float
)

fun main() {

    var itensMenu = mutableListOf<Produto>()
    var pedidos = mutableListOf<Pedido>()

    while (true) {
        println("1 - Cadastrar item")
        println("2 - Atualizar item")
        println("3 - Criar pedido")
        println("4 - Atualizar pedido")
        println("5 - Consultar pedidos")
        println("6 - Sair")

        val opcaoEscolhida: Int = readln().toInt()

        when(opcaoEscolhida) {
            1 -> {
                println("---CADASTRO DE ITEM---")
                println("Nome do item")
                val nomeItem: String = readln()
                println("Descrição do item")
                val descricaoItem: String = readln()
                println("Preço")
                val precoItem: Float = readln().toFloat()
                println("Quantidade em estoque")
                val estoqueItem: Int = readln().toInt()

                var novoProduto = Produto(nome = nomeItem, descricao = descricaoItem, preco = precoItem, estoque = estoqueItem,
                    codigo = itensMenu.size + 1)

                itensMenu.add(novoProduto)

            }
            2 -> {
                println("---ATUALIZAÇÃO ITEM---")
                if(itensMenu.isEmpty()) {
                    println("Sem itens no menu")
                } else {
                    println("Qual item deseja atualizar?")
                    for (item in itensMenu) {
                        println("${item.codigo} - ${item.nome}")
                    }
                    val itemSelecionado: Int = readln().toInt()

                    for (item in itensMenu) {
                        if (item.codigo == itemSelecionado) {
                            println("Deseja atualizar nome? Se sim, digite o novo nome, se não aperte Enter")
                            val novoNome: String = readln()
                            println("Deseja atualizar a descrição? Se sim, digite o novo nome, se não aperte Enter")
                            val novaDescricao: String = readln()
                            println("Deseja atualizar o preço? Se sim, digite o novo nome, se digite 0")
                            val novoPreco: Float = readln().toFloat()

                            if(novoNome != "") {
                                item.nome = novoNome
                            }

                            if(novaDescricao != "") {
                                item.descricao = novaDescricao
                            }

                            if(novoPreco != 0f) {
                                item.preco = novoPreco
                            }

                            println("Produto atualizado: $item")
                        }
                    }

                }



            }
            3 -> {

                println("---CRIAÇÃO DE PEDIDO---")
                println("1 - Adicionar itens ao pedido")
                println("2 - Finalizar pedido")

                val numeroPedido = when {
                    pedidos.isEmpty() -> 1
                    else -> pedidos[pedidos.lastIndex].numeroPedido + 1
                }

                val opcaoEscolhida: Int = readln().toInt()

                var itensEscolhidos = mutableListOf<Produto>()

                when (opcaoEscolhida) {
                    1 -> {

                        while (true) {

                            if(itensMenu.size == 0) {
                                println("Nenhum item cadastrado")
                                break
                            }

                            println("Qual item deseja adicionar ao pedido?")
                            for (item in itensMenu) {
                                println("${item.codigo} - ${item.nome} | Descrição: ${item.descricao} | " +
                                        "Preço: R$${item.preco} | Quantidade disponível: ${item.estoque}")
                            }

                            var codigoItemEscolhido: Int = readln().toInt()

                            val produtoEscolhido: Produto? = itensMenu.find {it.codigo == codigoItemEscolhido}

                            if(produtoEscolhido == null){
                                println("Produto nao encontrado")
                                break
                            }

                            while (true) {
                                println("Item escolhido: ${produtoEscolhido.nome}")
                                println("Deseja adicionar quantos? (estoque disponível: ${produtoEscolhido.estoque})")
                                val quantidade: Int = readln().toInt()

                                if(produtoEscolhido.estoque >= quantidade) {
                                    itensEscolhidos.add(produtoEscolhido)
                                    var novoPedido = Pedido(numeroPedido = numeroPedido, itens = itensEscolhidos,
                                        total = produtoEscolhido.preco * quantidade)

                                    produtoEscolhido.estoque = produtoEscolhido.estoque - quantidade

                                    pedidos.add(novoPedido)
                                    break
                                } else {
                                    println("Número no estoque indisponível")
                                    break
                                }
                            }
                        }
                    }
                    2 -> {
                        if(itensEscolhidos.isEmpty() ) {
                            println("Você deve escolher no minímo um item para finalizar o pedido")
                            break
                        }

                        println("Total do seu pedido: ${pedidos[numeroPedido].total}")
                        println("Deseja adicionar cupom?")
                    }
                }
            }
            6 -> break
        }
    }
}