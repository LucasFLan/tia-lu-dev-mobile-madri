data class Produto (
    var nome: String,
    var descricao: String,
    var preco: Float,
    var estoque: Int,
    var codigo: Int
)

fun main() {


    var isOnInterface: Boolean = true
    var itensMenu = mutableListOf<Produto>()

    do {
        println("1 - Cadastrar item")
        println("2 - Atualizar item")
        println("3 - Criar pedido")
        println("4 - Atualizar pedido")
        println("5 - Consultar pedidos")
        println("6 - Sair")

        val opcaoEscolhida: Int = readln().toInt()

        when (opcaoEscolhida) {
            1 -> {
                try {
                    println("---CADASTRO DE ITEM---")
                    println("Nome do item")
                    val nomeItem: String = readln()

                    println("Descrição do item")
                    val descricaoItem: String = readln()

                    println("Preço")
                    val precoItem: Float = readln().toFloat()

                    println("Quantidade em estoque")
                    val estoqueItem: Int = readln().toInt()

                    val novoProduto = Produto(
                        nome = nomeItem, descricao = descricaoItem, preco = precoItem, estoque = estoqueItem,
                        codigo = itensMenu.size + 1
                    )

                    itensMenu.add(novoProduto)
                } catch (e: NumberFormatException) {
                    println("Entrada inválida, digite um número para preço e estoque")
                }
            }
            2 -> {
                try {
                    println("---ATUALIZAÇÃO ITEM---")
                    if(itensMenu.isEmpty()) {
                        println("Sem itens no menu\n")
                    } else {
                        println("Qual item deseja atualizar?\n")

                        for (item in itensMenu) {
                            println("${item.codigo} - ${item.nome}")
                        }

                        val codigoItemSelecionado: Int = readln().toInt()

                        val itemSelecionado: Produto? = itensMenu.find { it.codigo == codigoItemSelecionado }

                        if(itemSelecionado == null) {
                            println("Item não encontrado")
                        } else {
                            println("Deseja atualizar nome? Se sim, digite o novo nome, se não aperte Enter")
                            val novoNome: String = readln()
                            println("Deseja atualizar a descrição? Se sim, digite o novo nome, se não aperte Enter")
                            val novaDescricao: String = readln()
                            println("Deseja atualizar o preço? Se sim, digite o novo preço, se não digite 0")
                            val novoPreco: Float = readln().toFloat()
                            println("Deseja atualizar o estoque? Se sim, digite a quantidade a ser somada no estoque, se não digite 0")
                            val novoEstoque: Int = readln().toInt()

                            if(novoNome != "") {
                                itemSelecionado.nome = novoNome
                            }

                            if(novaDescricao != "") {
                                itemSelecionado.descricao = novaDescricao
                            }

                            if(novoPreco != 0f){
                                itemSelecionado.preco = novoPreco
                            }

                            if(novoEstoque != 0) {
                                itemSelecionado.estoque += novoEstoque
                            }

                            println("Produto atualizado: $itemSelecionado")
                        }
                    }
                } catch (e: NumberFormatException) {
                    println("Entrada inválida, digite um número para preço e estoque")
                }

            }
            3 -> {
                println("---CRIAÇÃO DE PEDIDO---")

                var fazendoPedido = true
                var escolhendoItens = true
                var itensEscolhidos = mutableListOf<Produto>()
                var quantidade: Int = 0


                val numeroPedido = when {
                    pedidos.isEmpty() -> 0
                    else -> pedidos.lastIndex + 1
                }

                var novoPedido = Pedido(numeroPedido = numeroPedido, itens = itensEscolhidos, pagamento = "Em análise",
                    valor = 0f, status = OrderStatus.ACEITO)

                do {
                    println("1 - Adicionar itens ao pedido")
                    println("2 - Finalizar pedido")
                    println("3 - Limpar carrinho")
                    println("4 - Voltar")

                    val opcaoEscolhida: Int = readln().toInt()

                    when (opcaoEscolhida) {
                        1 -> {
                            do {
                                if(itensMenu.isEmpty()) {
                                    println("Nenhum item cadastrado\n")
                                    break
                                }

                                println("Qual item deseja adicionar ao pedido?\n")
                                for (item in itensMenu) {
                                    println("${item.codigo} - ${item.nome} | Descrição: ${item.descricao} | " +
                                            "Preço: R$${item.preco} | Quantidade disponível: ${item.estoque}")
                                }

                                val codigoItemEscolhido: Int = readln().toInt()

                                val produtoEscolhido: Produto? = itensMenu.find {it.codigo == codigoItemEscolhido}

                                if(produtoEscolhido == null){
                                    println("Produto nao encontrado")
                                    break
                                }

                                println("Item escolhido: ${produtoEscolhido.nome}")
                                println("Deseja adicionar quantos? (estoque disponível: ${produtoEscolhido.estoque})")
                                quantidade = readln().toInt()

                                if(produtoEscolhido.estoque < quantidade || quantidade == 0) {
                                    println("Número no estoque indisponível")
                                    break
                                } else {
                                    itensEscolhidos.add(produtoEscolhido)
                                    produtoEscolhido.estoque = produtoEscolhido.estoque - quantidade

                                    novoPedido.valor += (produtoEscolhido.preco * quantidade)

                                    println("${produtoEscolhido.nome} adicionado com sucesso\n")
                                }

                                escolhendoItens = false
                            } while (escolhendoItens)
                        }
                        2 -> {
                            var total: Float = 0f
                            if(novoPedido.itens.isEmpty()) {
                                println("Você deve escolher no minímo um item para finalizar o pedido")

                            } else {
                                println("Total do seu pedido: ${novoPedido.valor}")
                                println("Deseja adicionar cupom? Se sim, digite cupom, se não aperte Enter")
                                val cupom: String = readln()

                                val porcentagemDesconto: Float = 0.1f

                                if (cupom != "") {
                                    total = novoPedido.valor * (1 - porcentagemDesconto)
                                } else {
                                    total = novoPedido.valor
                                }

                                pedidos.add(novoPedido)

                                println("Pedido efetuado com sucesso. O total da sua conta é $total\n\n")

                                pedidos[numeroPedido].pagamento = "Pago"
                                fazendoPedido = false
                            }
                        }
                        3 -> {
                            if (itensEscolhidos.isEmpty()) {
                                println("O carrinho está vazio")
                            } else {
                                itensEscolhidos.forEach {
                                    itensMenu[it.codigo - 1].estoque += quantidade
                                }
                                novoPedido.valor = 0f
                                itensEscolhidos.clear()
                                println("Carrinho limpo com sucesso!\n")
                            }
                        }
                        4 -> {
                            if (itensEscolhidos.isEmpty()) {
                                fazendoPedido = false
                            } else {
                                println("Você deve finalizar o seu pedido ou limpar o seu carrinho!!!")
                            }
                        }
                    }
                } while (fazendoPedido)
            }
            6 -> isOnInterface = false
            else -> println("Opção inválida")

        }

    } while (isOnInterface)
}

