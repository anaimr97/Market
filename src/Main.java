import java.text.DecimalFormat;
import java.util.Scanner;

public class Main {

    public static Scanner input = new Scanner(System.in);
    public static Scanner input_txt = new Scanner(System.in);
    public static DecimalFormat df2 = new DecimalFormat("0.00");


    public static int total_produtos = 0;
    public static String[] lista_nome_produtos = new String[9999];
    public static double[] lista_precos_produtos = new double[9999];

    public static int total_vendas = 0;
    public static double saldo = 0.00;
    public static String[] lista_vendas = new String[9999];

    public static void main(String[] args) {

        System.out.println();

        int opcao;

        do{

            exibirMenu();

            System.out.print("Opção: ");
            opcao = input.nextInt();

            limpar();

            switch (opcao){
                case 1: registarProduto(); break;

                case 2:
                    if(total_produtos > 0){
                        editarProduto();
                    }
                    else{
                        informarErroCadastro();
                    }
                    break;
                case 3:
                    if(total_produtos > 0){
                        apagarProduto();
                    }
                    else{
                        informarErroCadastro();
                    }
                    break;
                case 4:
                    if(total_produtos > 0){
                        buscarPorBaliza();
                    }
                    else{
                        informarErroCadastro();
                    }
                    break;
                case 5:
                    if(total_produtos > 0){
                        listarProdutos();
                    }
                    else{
                        informarErroCadastro();
                    }
                    break;
                case 6:
                    if(total_produtos > 0){
                        registarVenda();
                    }
                    else{
                        informarErroCadastro();
                    }
                    break;
                case 7:
                    if(total_vendas > 0){
                        listarVendas();
                    }
                    else{
                        informarErroVenda();
                    }
                    break;
                case 0: sair(); break;
                default: informarErro(); break;
            }

            aguardar(2000);

            if(opcao!=0){

                clicarEnter();
                limpar();
            }

        }while(opcao != 0);

    }

    public static void registarProduto(){

        String nome_digitado;
        double preco_digitado;

        System.out.println("****** REGISTAR PRODUTO ******\n");

        System.out.print("- Digite o nome do produto a ser registado: ");
        nome_digitado = input_txt.nextLine();

        if(confimarNome(nome_digitado)){

            System.out.print("- Digite o preço deste produto: ");
            preco_digitado = input.nextDouble();

            System.out.println("\n*********************************\n");

            if(preco_digitado > 0){

                lista_nome_produtos[total_produtos] = nome_digitado;
                lista_precos_produtos[total_produtos] = preco_digitado;
                total_produtos++;

                System.out.println("SUCESSO, Produto registado!\n");

            }
            else{
                System.out.println("ERRO, preço inválido!\n");
            }
        }
        else{
            System.out.println("\n*********************************\n");
            System.out.println("ERRO, (" + nome_digitado + ") já está cadastrado!\n");
        }
    }

    public static boolean confimarNome(String nome){

        for(int i=0; i<total_produtos; i++){

            if(nome.equalsIgnoreCase(lista_nome_produtos[i])){
                return false;
            }
        }

        return true;
    }

    public static void listarProdutos(){

        System.out.println("****** LISTA DE PRODUTOS ******\n");

        for (int i=0; i<total_produtos; i++){

            System.out.println("(" + i + ") - {" + lista_nome_produtos[i] + "} [" + df2.format(lista_precos_produtos[i]) + "€].");
        }

    }

    public static void editarProduto(){

        int codigo;
        String nome_digitado;
        double preco_digitado;

        System.out.println("****** EDITAR PRODUTO ******\n");

        listarProdutos();

        System.out.println("\n*********************************\n");

        System.out.print("\nDigite o código do produto a ser editado (0 até " + (total_produtos -1) + "): ");
        codigo = input.nextInt();

        System.out.println("\n*********************************\n");

        if(codigo >= 0 && codigo <=(total_produtos-1)){

            System.out.print("Digite o nome do produto a ser registado: ");
            nome_digitado = input_txt.nextLine();

            if(confimarNome(nome_digitado) || nome_digitado.equalsIgnoreCase(lista_nome_produtos[codigo])){

                System.out.print("Digite o preço deste produto: ");
                preco_digitado = input.nextDouble();

                System.out.println("\n*********************************\n");

                if(preco_digitado > 0){
                    lista_nome_produtos[codigo] = nome_digitado;
                    lista_precos_produtos[codigo] = preco_digitado;

                    System.out.println("SUCESSO, Produto editado!\n");

                }
                else{
                    System.out.println("ERRO, preço inválido!\n");
                }
            }
            else{
                System.out.println("\n*********************************\n");
                System.out.println("ERRO, (" + nome_digitado + ") já está cadastrado!\n");
            }
        }
        else{
            System.out.println("\n*********************************\n");
            System.out.println("ERRO, código não existe!");

        }

    }

    public static void apagarProduto(){

        int codigo;

        System.out.println("****** APAGAR PRODUTO ******\n");

        listarProdutos();

        System.out.println("\n*********************************\n");

        System.out.print("\nDigite o código do produto a ser apagado (0 até " + (total_produtos -1) + "): ");
        codigo = input.nextInt();

        System.out.println("\n*********************************\n");

        if(codigo >= 0 && codigo <=(total_produtos-1)){

            for(int i=codigo; i<(total_produtos-1); i++){
                lista_nome_produtos[i] = lista_nome_produtos[i+1];
                lista_precos_produtos[i] = lista_precos_produtos[i+1];
            }
            total_produtos--;

            System.out.println("SUCESSO, Produto apagado!");
        }
        else{
            System.out.println("ERRO, código não existe!");
        }
    }

    public static void buscarPorBaliza(){

        double preco_minimo, preco_maximo;
        boolean encontrado = false;

        System.out.println("****** BUSCAR POR BALIZA DE PREÇOS ******\n");

        System.out.print("Digite o valor mínimo de preço para a busca: ");
        preco_minimo = input.nextDouble();
        System.out.print("Digite o valor máximo de preço para a busca: ");
        preco_maximo = input.nextDouble();

        System.out.println("\n*********************************\n");

        for(int i=0; i<total_produtos; i++){

            if(lista_precos_produtos[i] >= preco_minimo && lista_precos_produtos[i] <= preco_maximo){

                encontrado = true;

                System.out.println("(" + i + ") - {" + lista_nome_produtos[i] + "} [" + df2.format(lista_precos_produtos[i]) + "€].");
            }
        }

        if(!encontrado){

            System.out.println("ERRO, não existem produtos na baliza de (" + df2.format(preco_minimo) + "€) a (" + df2.format(preco_maximo) + "€)!");
        }

    }

    public static void registarVenda(){

        int quantidade, codigo;
        double total;


        System.out.println("****** REGISTAR VENDA ******");

        System.out.println("\n*********************************\n");

        listarProdutos();

        System.out.println("\n*********************************\n");

        System.out.print("Digite o código do produto a ser vendido (0 até " + (total_produtos -1) + "): ");
        codigo = input.nextInt();

        System.out.println("\n*********************************\n");

        if(codigo >= 0 && codigo <=(total_produtos-1)){

            System.out.print("Digite a quantidade de " + lista_nome_produtos[codigo] + " que vai vender: ");
            quantidade = input_txt.nextInt();

            System.out.println("\n*********************************\n");

            if(quantidade >= 0){

                total = quantidade * lista_precos_produtos[codigo];
                df2.format(total);

                lista_vendas[total_vendas] = "- (" + lista_nome_produtos[codigo] + ") X " + Integer.toString(quantidade) + " = [" + Double.toString(total) + "€]";

                System.out.println("\n" + lista_vendas[total_vendas]);

                System.out.println("SUCESSO");

                total_vendas++;
                saldo += total;
            }
            else{
                System.out.println("ERRO, quantidade inválida!\n");
            }
        }
        else{

            System.out.println("ERRO, código não existe!");

        }

    }

    public static void listarVendas(){

        System.out.println("****** LISTA DE VENDAS ******\n");

        for(int i=0; i<total_vendas; i++){

            System.out.println(lista_vendas[i]);
        }

        System.out.println("\n*********************************\n");

        System.out.println("Total de vendas: " + total_vendas);
        System.out.println("Saldo total das vendas: " + df2.format(saldo) + "€");
    }

    public static void sair(){

        System.out.println("\n*********************************\n");

        if(total_vendas > 0){

            System.out.println("Total de vendas: " + total_vendas);
            System.out.println("Saldo total das vendas: " + df2.format(saldo) + "€");
        }
        else{
            System.out.println("Nenhuma venda foi efetuada!");
        }

        System.out.println("\n*********************************\n");

        System.out.println("A sair...");
    }

    public static void exibirMenu(){

        System.out.println("===== MERCADO =====\n");

        System.out.println("1 - Registar Produto.");
        System.out.println("2 - Editar Produto.");
        System.out.println("3 - Apagar Produto.");
        System.out.println("4 - Buscar produto por baliza de preços.");
        System.out.println("5 - Listar todos os produtos.\n");

        System.out.println("6 - Registar venda.");
        System.out.println("7 - Listar todas as vendas.\n");

        System.out.println("0 - Sair.\n");
    }

    public static void informarErroCadastro(){

        System.out.println("ERRO, não existe nenhum produto cadastrado!");
    }

    public static void informarErroVenda(){

        System.out.println("ERRO, não foi feita nenhuma venda!");
    }

    public static void informarErro(){

        System.out.println("ERRO, opção inválida!");
    }


    public static void limpar(){

        for(int i=0; i<25; i++){
            System.out.println();
        }
    }

    public static void aguardar(int ms){

        try{
            Thread.sleep(ms);
        }catch (InterruptedException erro){
            erro.printStackTrace();
        }
    }

    public static void clicarEnter(){

        String enter;

        System.out.println("\n*********************************\n");
        System.out.println("Prima a tecla <ENTER> para continuar...");
        enter = input_txt.nextLine();
    }
}