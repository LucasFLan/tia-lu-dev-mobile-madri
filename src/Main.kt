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

            6 -> isOnInterface = false
            else -> println("Opção inválida")
        }
    } while (isOnInterface)
}