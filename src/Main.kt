data class Produto ( // nesse caso, o Produto refere-se ao Item do Cardápio
    var nome: String,
    var descricao: String,
    var preco: Float, // eu recomendo usar o BigDecimal
    var estoque: Int,
    var codigo: Int
)

enum class OrderStatus{ // aqui você tinha que padronizar se iria usar inglês ou português no código
    ACEITO,
    FAZENDO,
    FEITO,
    ESPERANDO_ENTREGADOR,
    SAIU_PARA_ENTREGA,
    ENTREGUE
}

data class Pedido (
    var numeroPedido: Int,
    var itens: MutableList<Produto>,
    var pagamento: String,
    var status: OrderStatus,
    var valor: Float
)

// o interessante era ter um OrderItem
// que tem um produto e a quantidade

fun main() {

    var itensMenu = mutableListOf<Produto>()  //essa variável deveria se chamada de itensCardapio, itens ou produtos,
    // que vocês definiram o data class como produto. Outro ponto, itensMenu pode ter um significado ambiguo, pois
    // temos um menu em linha de código
    // a variável deveria ter sido declara com val, pois apesar de tem uma lista dentro dele, o valor da variável
    // itensMenu não muda, pois sempre será uma lista.
    var pedidos = mutableListOf<Pedido>() // variável deveria ter sido declara como val pedidos

    var isOnInterface: Boolean = true // aqui de novo temos o problema de variáveis em pt-br e em en-us.
    // padronizar em qual língua vão programar

    do {

        // aqui era interessante apresentar um título de menu
        println("1 - Cadastrar item")
        println("2 - Atualizar item")
        println("3 - Criar pedido")
        println("4 - Atualizar pedido")
        println("5 - Consultar pedidos")
        println("6 - Sair")

        val opcaoEscolhida: Int = readln().toInt() // e se o usuário digitar uma letra, o sistema para e acabou?
        // o sistema é como robusto a falhas na entrada de dados.

        when(opcaoEscolhida) {
            1 -> {
                try {
                    println("---CADASTRO DE ITEM---")
                    println("Nome do item")
                    val nomeItem: String = readln() // aqui não precisava definir o tipo, pois o Kotlin faria
                    // a inferência de tipagem dinamicamente, mas é bom ver que vocês sabem declarar
                    //variáveis de entrada com val.

                    println("Descrição do item")// colocaria um : no final da frase
                    val descricaoItem: String = readln() // mesa coisa aqui

                    println("Preço")
                    val precoItem: Float = readln().toFloat() // e se tiver um erro na entrada o cadastro todo é
                    // perdido? não teria sido interessante a validação por campo sensível (esse que tem conversão
                    // de string para tipo numérico.
                    // na segunda etapa desse projeto, eu sugiro que vocês criem uma função para leitura de valor
                    // vocês passariam a mensagem, o tipo esperado e toda validação seria feita

                    println("Quantidade em estoque")
                    val estoqueItem: Int = readln().toInt() // mesa coisa

                    // gostei de terem feito a criação de um novo poduto usando a chamada de função com a passagem de parâmetros
                    val novoProduto = Produto(nome = nomeItem, descricao = descricaoItem, preco = precoItem,
                        estoque = estoqueItem,
                        codigo = itensMenu.size + 1) // sacada inteligente essa de usar o tamanho da lista
                    // só tem um problema: se eu remover um item do meio da lista e inserir um novo,
                    // eu vou ter dois itens com o mesmo código, isso pode ser um problema.
                    // Atualmente, o código não exclui um item, logo, vai funcionar
                    // só que seu código tem um ponto de falha.
                    // ao criar uma nova funcionalidade como excluir item,
                    // o seu código vai quebrar uma funcionalidade já existente

                    itensMenu.add(novoProduto)
                } catch (e: NumberFormatException){
                    println("Entrada inválida, digite um número para preço e estoque") // a mensagem de erro não é
                    // personalizada ao erro o que dificulta que o usuário saiba de fato onde tem o problema.
                }
            }
            2 -> {
                try {
                    println("---ATUALIZAÇÃO ITEM---")
                    if(itensMenu.isEmpty()) {
                        println("Sem itens no menu\n")
                    } else {
                        println("Qual item deseja atualizar?\n")

                        // dê preferência a usar o forEach das collections
                       // itensMenu.forEach { item -> "${item.codigo} - ${item.nome}"}

                        for (item in itensMenu) {
                            println("${item.codigo} - ${item.nome}")
                        }

                        val codigoItemSelecionado: Int = readln().toInt() // declaração usando val, boa, mas temos o problema
                        // da conversão para Int que tem uma mensagem genérica:
                        //"Entrada inválida, digite um número para preço e estoque"
                        // que não contempla esse problema

                        // muito bom o uso do find, isso simplifica operações de busca no código
                        val itemSelecionado: Produto? = itensMenu.find { it.codigo == codigoItemSelecionado }

                        if(itemSelecionado == null) {
                            println("Item não encontrado")
                        } else {
                            // eu gostei dessa ideia de validação, vocês podem fazer disso uma função
                            // a função retorna o valor atual se for pressionado enter ou o novo valor caso contrário
                            // seria legal nela ter a conversão do tipo que se quer
                            // e repetir quantas vezes forem necessárias até que um valor válido seja informado
                            println("Deseja atualizar nome? Se sim, digite o novo nome, se não aperte Enter")
                            val novoNome: String = readln()
                            println("Deseja atualizar a descrição? Se sim, digite o novo nome, se não aperte Enter")
                            val novaDescricao: String = readln()
                            println("Deseja atualizar o preço? Se sim, digite o novo preço, se não digite 0")
                            val novoPreco: Float = readln().toFloat()
                            println("Deseja atualizar o estoque? Se sim, digite a quantidade a ser somada no estoque, se não digite 0")
                            val novoEstoque: Int = readln().toInt()

                            if(novoNome != "") { // achava legal vocês removerem todos os espaços em branco também
                                // não apenas o enter, mas se foi tudo em branco, não deve ser considerado
                                // usem a função trim de string
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
                    println("Entrada inválida, digite um número para preço e estoque") // mensagem genérica demais
                }

            }
            3 -> {
                println("---CRIAÇÃO DE PEDIDO---")

                var fazendoPedido = true
                var escolhendoItens = true
                var itensEscolhidos = mutableListOf<Produto>() // aqui poderia ser declarado como val
                var quantidade: Int = 0

                // aqui poderia ter um contador mesmo que incrementava toda vez que um novo pedido fosse criado
                val numeroPedido = when {
                    pedidos.isEmpty() -> 0
                    else -> pedidos.lastIndex + 1
                }

                // essa variável pode ser declarada como val, pois são os seus campos que serão modificados
                var novoPedido = Pedido(numeroPedido = numeroPedido, itens = itensEscolhidos, pagamento = "Em análise",
                    valor = 0f, status = OrderStatus.ACEITO) // o pedido deveria ser a última coisa a ser criada nesse processo

                do {
                    // acho que adicionou complexidade, gosto, mas poderia ter sido mais simples
                    println("1 - Adicionar itens ao pedido")
                    println("2 - Finalizar pedido")
                    println("3 - Limpar carrinho")
                    println("4 - Voltar")

                    val opcaoEscolhida: Int = readln().toInt()

                    when (opcaoEscolhida) {
                        1 -> {
                            do {
                                // perceba que você já criou um novo pedido, mas só depois você valida se tem itens
                                // como você autoriza criar um pedido sem itens do cardápio cadastrado?
                                if(itensMenu.isEmpty()) {
                                    println("Nenhum item cadastrado\n")
                                    break // não é um bom uso, o ideal era substituir isso por um else
                                } // adicionar o else com o bloco de código abaixo

                                println("Qual item deseja adicionar ao pedido?\n")
                                for (item in itensMenu) {
                                    println("${item.codigo} - ${item.nome} | Descrição: ${item.descricao} | " +
                                            "Preço: R$${item.preco} | Quantidade disponível: ${item.estoque}")
                                }

                                //bom uso do val
                                val codigoItemEscolhido: Int = readln().toInt() // problema de conversão

                                // bom uso do find e do val
                                val produtoEscolhido: Produto? = itensMenu.find {it.codigo == codigoItemEscolhido}

                                if(produtoEscolhido == null){
                                    println("Produto nao encontrado")
                                    break // substitua sempre esses breaks e continue por elses nessas condições de if
                                    // nesses casos
                                }

                                println("Item escolhido: ${produtoEscolhido.nome}")
                                println("Deseja adicionar quantos? (estoque disponível: ${produtoEscolhido.estoque})")
                                quantidade = readln().toInt() // problema de conversão

                                // essa condição pode ser melhor escrita:
                                // quantidade <= 0 || quantidade > produtoEscolhido.estoque
                                if(produtoEscolhido.estoque < quantidade || quantidade == 0) {
                                    // você pode mudar a mensagem informando que a quantidade solicitada é inválida:
                                    // a quantidade deve ser no mínimo de 1 item e não pode ultrapassar o estoque de X unidades
                                    println("Número no estoque indisponível")
                                    break // não precisava desse break
                                } else {
                                    itensEscolhidos.add(produtoEscolhido)

                                    // essa atualização de estoque deveria ser feita apenas depois de
                                    // inserir todos os itens no pedido
                                    // garantindo que não vamos ter um erro no meio do caminho
                                    // que descontou tudo
                                    produtoEscolhido.estoque = produtoEscolhido.estoque - quantidade

                                    //também deveria ser processado no final do pedido
                                    // no caso do código de vocês, vocês iam precisar de outro data class
                                    // um ItemPedido ou OrderItem que tem o produto e a quantidade comprada
                                    novoPedido.valor += (produtoEscolhido.preco * quantidade)

                                    println("${produtoEscolhido.nome} adicionado com sucesso\n")
                                }

                                escolhendoItens = false // estaria dentro do else e teria um com true no if
                            } while (escolhendoItens)
                        }
                        2 -> {
                            var total: Float = 0f // dê preferência a BigDecimal ou no mínimo Double
                            if(novoPedido.itens.isEmpty()) {
                                println("Você deve escolher no minímo um item para finalizar o pedido")
                            } else {
                                println("Total do seu pedido: ${novoPedido.valor}")
                                println("Deseja adicionar cupom? Se sim, digite cupom, se não aperte Enter")
                                val cupom: String = readln()

                                val porcentagemDesconto: Float = 0.1f //dê preferência ao BigDecimal

                                // uso ligeiramente errado do when, pois está pouco idiomático
                                // nesse caso o when seria bom como expressão
                                /**
                                 * val totalComDesconto = when(cupom.uppercase()) {
                                 *                                     "SIM" -> novoPedido.valor * (1 - porcentagemDesconto)
                                 *                                     else -> novoPedido.valor
                                 *                                 }
                                 */
                                when {
                                    // condição não condiz com o que foi apresentado ao usuário no print.
                                    cupom == "DEZ" -> total = novoPedido.valor * (1 - porcentagemDesconto)
                                    cupom == "" -> {
                                        // a condição faz duas coisas: imprime e calcula, isso não é bom!!!
                                        println("Pagamento sem cupom")
                                        total = novoPedido.valor
                                    }
                                    else -> {
                                        // a condição faz duas coisas: imprime e calcula, isso não é bom!!!
                                        println("Cupom inválido")
                                        total = novoPedido.valor
                                    }
                                }

                                pedidos.add(novoPedido) //pedido adicionado
                                // aqui que deveria ser feita as atualizações de estoque

                                println("Pedido efetuado com sucesso. O total da sua conta é $total\n\n")

                                pedidos[numeroPedido].pagamento = "Pago" //todos os pedidos são pagos por padrão
                                fazendoPedido = false // controle do pedido
                            }
                        }
                        3 -> {
                            if (itensEscolhidos.isEmpty()) {
                                println("O carrinho está vazio")
                            } else {
                                itensEscolhidos.forEach {
                                    itensMenu[it.codigo - 1].estoque += quantidade  // essa quantidade não é por produto
                                    // ela acaba sendo a última quantidade lida
                                    //o que faz a atualização do estoque
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
            4 -> {
                println("---ATUALIZAÇÃO DO STATUS DO PEDIDO---")

                if(pedidos.isEmpty()) {
                    println("Sem pedidos por aqui\n")
                } else {
                    println("Escolha o pedido para atualizar o status\n")
                    println("PEDIDOS:")
                    //vocês deveriam dar prioridade a usar o forEach
                    // sempre que usarmos as collections
                    for (pedido in pedidos) {
                        println("Número do pedido: ${pedido.numeroPedido} - ${pedido.status}\n - total: R$${pedido.valor}")
                    }

                    val codigoPedidoEscolhido: Int = readln().toInt() // potencial problema de conversão

                    //bom uso do find
                    val pedidoEscolhido: Pedido? = pedidos.find {
                        it.numeroPedido == codigoPedidoEscolhido
                    }

                    if (pedidoEscolhido == null) {
                        println("Pedido não encontrado")
                    } else {
                        println("Deseja atualizar o pedido para qual Status? Status atual " +
                                "do pedido ${pedidoEscolhido.numeroPedido}: ${pedidoEscolhido.status}\n")

                        //aqui era legal que a gente tivesse feito um menu a partir do enum
                        //OrderStatus.entries.forEachIndexed { index, orderStatus -> println("${index + 1} - $orderStatus") }

                        println("1 - FAZENDO")
                        println("2 - FEITO")
                        println("3 - ESPERANDO ENTREGADOR")
                        println("4 - SAIU PARA ENTREGA")
                        println("5 - ENTREGUE")

                        val statusEscolhido: Int = readln().toInt()

                        val numeroPedido = pedidoEscolhido.numeroPedido
                        // o pedido escolhido é o que deve ser atualizado
                        // essa variável não faz sentido

                        // aqui poderia ser;
                        //pedidoEscolhido.status = OrderStatus.entries[statusEscolhido - 1]


                        // esse when é pouco idiomático, pois temos para a mesa variável várias atribuições
                        // vocês deveriam ter escrito tipo:

                        /***
                         * pedidos[numeroPedido].status = when(statusEscolhido) {
                            *   1 ->  OrderStatus.FAZENDO
                            *   2 -> OrderStatus.FEITO
                            *   3 -> OrderStatus.ESPERANDO_ENTREGADOR
                            *   4 -> OrderStatus.SAIU_PARA_ENTREGA
                            *   5 -> OrderStatus.ENTREGUE
                         * }
                         */
                        when(statusEscolhido) {
                            1 -> pedidos[numeroPedido].status = OrderStatus.FAZENDO
                            2 -> pedidos[numeroPedido].status = OrderStatus.FEITO
                            3 -> pedidos[numeroPedido].status = OrderStatus.ESPERANDO_ENTREGADOR
                            4 -> pedidos[numeroPedido].status = OrderStatus.SAIU_PARA_ENTREGA
                            5 -> pedidos[numeroPedido].status = OrderStatus.ENTREGUE
                        }

                        println("Status do pedido ${pedidoEscolhido.numeroPedido} alterado para ${pedidoEscolhido.status} " +
                                "com sucesso\n")
                    }
                }

            }
            5 -> {
                println("---CONSULTA DE PEDIDOS---")

                var estaConsultando: Boolean = true // acho que aqui seria legal
                //esse valor precisa ser atualizado

                do {

                    if(pedidos.isEmpty()) {
                        println("Sem pedidos por enquanto!\n")
                        break //substituir por else
                    }

                    println("Vizualizar pedidos:")
                    // fazer aquele forEach para os status do enum
                    println("1 - VER TODOS")
                    println("2 - ACEITO")
                    println("3 - FAZENDO")
                    println("4 - FEITO")
                    println("5 - ESPERANDO ENTREGADOR")
                    println("6 - SAIU PARA ENTREGA")
                    println("7 - ENTREGUE")
                    println("8 - Sair da consulta")

                    val opcaoEscolhida: Int = readln().toInt() //problemas de conversão
                    var pedidosPorStatus: List<Pedido> = listOf()

                    //expressão pouco idiomática
                    //o correto deveria ser:
                    // val pedidosPorStatus: List<Pedido> = when(opcaoEscolhida) {}

                    when (opcaoEscolhida) {
                        1 -> {
                            // retornaria a pedidos
                            for (pedido in pedidos) {
                                println("Pedido ${pedido.numeroPedido} - Itens: ${pedido.itens} - VALOR: R$${pedido.valor} - ${pedido.pagamento} - ${pedido.status}")
                            }
                        }
                        2 -> pedidosPorStatus = pedidos.filter { it.status == OrderStatus.ACEITO }
                        3 -> pedidosPorStatus = pedidos.filter { it.status == OrderStatus.FAZENDO }
                        4 -> pedidosPorStatus = pedidos.filter { it.status == OrderStatus.FEITO }
                        5 -> pedidosPorStatus = pedidos.filter { it.status == OrderStatus.ESPERANDO_ENTREGADOR }
                        6 -> pedidosPorStatus = pedidos.filter { it.status == OrderStatus.SAIU_PARA_ENTREGA }
                        7 -> pedidosPorStatus = pedidos.filter { it.status == OrderStatus.ENTREGUE }
                        8 -> {
                            println("Saindo...") // deveria colocar como opção inválida apenas
                            break // não é uma boa prática
                        }
                        else -> {
                            println("Opção inválida!") // retornar a lista vazia
                            break // não é uma boa prática
                        }
                    }

                    if (pedidosPorStatus.isEmpty() && opcaoEscolhida != 1) {
                        println("Nenhum pedido encontrado com esse status")
                    } else {
                        //deveria dar prioridade ao forEach
                        for (pedido in pedidosPorStatus) {
                            println("Pedido ${pedido.numeroPedido} - Itens: ${pedido.itens} - VALOR: R$${pedido.valor} - ${pedido.pagamento} - ${pedido.status}")
                        }
                    }

                } while (estaConsultando)
            }
            6 -> isOnInterface = false // aaa é a opção de controle
            else -> println("Opção inválida")
        }
    } while (isOnInterface)
}