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

            6 -> isOnInterface = false
            else -> println("Opção inválida")
        }
    } while (isOnInterface)
}