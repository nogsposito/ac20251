package br.edu.cs.poo.ac.seguro.mediators;

import static br.edu.cs.poo.ac.seguro.mediators.StringUtils.ehNuloOuBranco;
import static br.edu.cs.poo.ac.seguro.mediators.ValidadorCpfCnpj.ehCnpjValido;
import static br.edu.cs.poo.ac.seguro.mediators.ValidadorCpfCnpj.ehCpfValido;

import java.math.BigDecimal;
import java.time.LocalDate;

import br.edu.cs.poo.ac.seguro.daos.ApoliceDAO;
import br.edu.cs.poo.ac.seguro.daos.SeguradoEmpresaDAO;
import br.edu.cs.poo.ac.seguro.daos.SeguradoPessoaDAO;
import br.edu.cs.poo.ac.seguro.daos.SinistroDAO;
import br.edu.cs.poo.ac.seguro.daos.VeiculoDAO;
import br.edu.cs.poo.ac.seguro.entidades.Apolice;
import br.edu.cs.poo.ac.seguro.entidades.CategoriaVeiculo;
import br.edu.cs.poo.ac.seguro.entidades.PrecoAno;
import br.edu.cs.poo.ac.seguro.entidades.Segurado;
import br.edu.cs.poo.ac.seguro.entidades.SeguradoEmpresa;
import br.edu.cs.poo.ac.seguro.entidades.SeguradoPessoa;
import br.edu.cs.poo.ac.seguro.entidades.Sinistro;
import br.edu.cs.poo.ac.seguro.entidades.Veiculo;

public class ApoliceMediator {

    private static ApoliceMediator apoliceMediator = ApoliceMediator.getInstancia();
	private static ApoliceMediator instancia = new ApoliceMediator();

	private SeguradoPessoaDAO daoSegPes;
	private SeguradoEmpresaDAO daoSegEmp;
	private VeiculoDAO daoVel;
	private ApoliceDAO daoApo;
	private SinistroDAO daoSin;

	private ApoliceMediator() {}

    public static ApoliceMediator getInstancia(){
        return instancia;
    }
    
    public RetornoInclusaoApolice incluirApolice(DadosVeiculo dados) {
		
        String msg = validarTodosDadosVeiculo(dados);
        if (msg != null){
            return new RetornoInclusaoApolice(null, msg);
        }

        Veiculo veiculo = daoVel.buscar(dados.getPlaca());
        if (veiculo == null){
            return new RetornoInclusaoApolice(null, "Veículo não cadastrado");
        }

        String numero = gerarNumero(dados.getCpfOuCnpj(), dados.getPlaca());
        if (numero == null) {
            return new RetornoInclusaoApolice(null, "Apólice já existente para ano atual e veículo");
        }
        if (daoApo.buscar(numero) != null){
            return new RetornoInclusaoApolice(null, "Apólice já existente para ano atual e veículo");
        }

        Segurado segurado;
        if (veiculo.getProprietarioPessoa() != null){
            segurado = veiculo.getProprietarioPessoa();
        } else {
            segurado = veiculo.getProprietarioEmpresa();
        }

        boolean ehLocadoraDeVeiculos = false;
        if (segurado instanceof SeguradoEmpresa){
            SeguradoEmpresa empresa = (SeguradoEmpresa) segurado;
            ehLocadoraDeVeiculos = empresa.isEhLocadoraDeVeiculos();
        }

        BigDecimal premio = calcularPremio(segurado, ehLocadoraDeVeiculos, dados, new BigDecimal("0.03"));
        BigDecimal franquia = premio.multiply(new BigDecimal("0.75"));

        daoApo.incluir(new Apolice(numero, veiculo, franquia, premio, dados.getValorMaximoSegurado(), LocalDate.now()));

        return new RetornoInclusaoApolice(numero, null);
	}

	/*
	 * Ver os testes test19 e test20
	 */
	public Apolice buscarApolice(String numero) {
		return daoApo.buscar(numero);
	}
	/*
	 * A exclusão não é permitida quando: 
	 * 1- O número for nulo ou branco.
	 * 2- Não existir apólice com o número recebido.
	 * 3- Existir sinistro cadastrado no mesmo ano 
	 *    da apólice (comparar ano da data e hora do sinistro
	 *    com ano da data de início de vigência da apólice) 
	 *    para o mesmo veículo (comparar o veículo do sinistro
	 *    com o veículo da apólice usando equals na classe veículo,
	 *    que deve ser implementado). Para obter os sinistros 
	 *    cadastrados, usar o método buscarTodos do dao de sinistro, 
	 *    implementado para contempar a questão da bonificação 
	 *    no método de incluir apólice.
	 *    É possível usar LOMBOK para implementação implicita do
	 *    equals na classe Veiculo.
	 */
	public String excluirApolice(String numero) {
		return null;
	}
	/*
	 * Daqui para baixo estão SUGESTÕES de métodos que propiciariam
	 * mais reuso e organização de código.
	 * Eles poderiam ser chamados pelo método de inclusão de apólice.
	 * Mas...é apenas uma sugestão. Vocês podem fazer o código da 
	 * maneira que acharem pertinente. 
	 */
	private String validarTodosDadosVeiculo(DadosVeiculo dados) {

		SeguradoPessoaMediator seguradoPessoaMediator = SeguradoPessoaMediator.getInstancia();
		SeguradoEmpresaMediator empresaMediator = SeguradoEmpresaMediator.getInstancia();
		SeguradoPessoaDAO seguradoPessoaDAO = new SeguradoPessoaDAO();
		SeguradoEmpresaDAO seguradoEmpresaDAO = new SeguradoEmpresaDAO();

        Veiculo vel = null;
		boolean pessoa = true;
		SeguradoPessoa seguradoPessoa = null;
		SeguradoEmpresa seguradoEmpresa = null;

		daoApo = new ApoliceDAO();
		daoVel = new VeiculoDAO();
		daoVel = new VeiculoDAO();
        daoSegPes = new SeguradoPessoaDAO();
        daoSegEmp = new SeguradoEmpresaDAO();
    
        if (dados == null){
            return "Dados do veículo devem ser informados";
        }

        if (ehNuloOuBranco(dados.getCpfOuCnpj())){
            return "CPF ou CNPJ deve ser informado";
        }
        if (dados.getCpfOuCnpj().length() == 11){
            if (!ehCpfValido(dados.getCpfOuCnpj())){
                return "CPF inválido";
            }
        } else if (dados.getCpfOuCnpj().length() == 14){
            if (!ehCnpjValido(dados.getCpfOuCnpj())){
                return "CNPJ inválido";
            }
        } else {
            return "CPF ou CNPJ deve ser informado";
        }

        if(dados.getPlaca() == null || dados.getPlaca().isBlank()){
			return "Placa do veículo deve ser informada";
        }

        if (dados.getAno() > 2025 || dados.getAno() < 2020){
            return "Ano tem que estar entre 2020 e 2025, incluindo estes";
        }
        
        BigDecimal valorMaximoSegurado = dados.getValorMaximoSegurado();
        if (valorMaximoSegurado == null){
            return "Valor máximo segurado deve ser informado";
        }
    
        CategoriaVeiculo categoria = obterCategoriaPorCodigo(dados.getCodigoCategoria());
        if (categoria == null){
            return "Categoria inválida";
        }

        BigDecimal valorCarro = obterValorCarroParaAno(categoria, dados.getAno());
        if (valorCarro.compareTo(BigDecimal.ZERO) == 0){
            return "Valor do carro não encontrado para o ano";
        }

        if (valorMaximoSegurado.compareTo(valorCarro.multiply(new BigDecimal("0.75"))) < 0){
            return "Valor máximo segurado deve estar entre 75% e 100% do valor do carro encontrado na categoria";
        }
        if (valorMaximoSegurado.compareTo(valorCarro.multiply(new BigDecimal("1.00"))) > 0){
            return "Valor máximo segurado deve estar entre 75% e 100% do valor do carro encontrado na categoria";
        }

        if (dados.getCodigoCategoria() < 1 || dados.getCodigoCategoria() > 5){
            return "Categoria inválida";
        }

        if (ehCpfValido(dados.getCpfOuCnpj())){
            if (daoSegPes.buscar(dados.getCpfOuCnpj()) == null){
                return "CPF inexistente no cadastro de pessoas";
            }
        } else if (ehCnpjValido(dados.getCpfOuCnpj())){
            if (daoSegEmp.buscar(dados.getCpfOuCnpj()) == null){
                return "CNPJ inexistente no cadastro de empresas";
            }
        }

        return null;
	}

	private String validarCpfCnpjValorMaximo(DadosVeiculo dados) {
		return null;
	}

    private BigDecimal obterValorCarroParaAno(CategoriaVeiculo categoria, int ano){
        for (PrecoAno precoAno : categoria.getPrecosAnos()){
            if (precoAno.getAno() == ano) return BigDecimal.valueOf(precoAno.getPreco());
        }
        return BigDecimal.ZERO;
    }

    private CategoriaVeiculo obterCategoriaPorCodigo(int codigo){
        for (CategoriaVeiculo categoria : CategoriaVeiculo.values()) {
			if (categoria.getCodigo() == codigo) {
				return categoria;
			}
		}
		return null;
    }

	private BigDecimal obterValorMaximoPermitido(int ano, int codigo) {
		for (CategoriaVeiculo categoria : CategoriaVeiculo.values()) {
			if (categoria.getCodigo() == codigo) {
				for (PrecoAno precoAno : categoria.getPrecosAnos()) {
					if (precoAno.getAno() == ano) {
						return BigDecimal.valueOf(precoAno.getPreco());
					}
				}
			}
		}
		return BigDecimal.ZERO;
	}

    private String gerarNumero(String cpfOuCnpj, String placa){
        if(cpfOuCnpj.length() == 1){
            return String.valueOf(LocalDate.now().getYear()) + "000" + cpfOuCnpj + placa;
        } else if (cpfOuCnpj.length() == 14){
            return String.valueOf(LocalDate.now().getYear()) + cpfOuCnpj + placa;
        }
        return null;
    }

    private BigDecimal calcularPremio(Segurado segurado, boolean EhLocadoraDeVeiculos, DadosVeiculo dados, BigDecimal percentual){
        
        BigDecimal vpa = percentual.multiply(dados.getValorMaximoSegurado());
        BigDecimal vpb;

        if (EhLocadoraDeVeiculos){
            vpb = vpa.multiply(new BigDecimal("1.2"));
        } else {
            vpb = vpa;
        }

        BigDecimal vpc = vpb.subtract(segurado.getBonus().divide(BigDecimal.TEN));

        if (vpc.compareTo(BigDecimal.ZERO) > 0){
            return vpc;
        }

        return BigDecimal.ZERO;
    }

}


	/*
	 * Algumas regras de validação e lógicas devem ser inferidas através da leitura e 
	 * do entendimento dos testes automatizados. Seguem abaixo explicações pertinentes
	 * e necessárias ao entendimento completo de como este método deve funcionar.
	 * 
	 *  Toda vez que um retorno contiver uma mensagem de erro não nula, significando
	 *  que a apólice não foi incluída, o numero da apólice no retorno deve ser nulo.
	 *  
	 *  Toda vez que um retorno contiver uma mensagem de erro não nula, significando
	 *  que a apólice não foi incluída, o numero da apólice no retorno deve ser nulo.
	 *  
	 *  Toda vez que um retorno contiver uma mensagem de erro nula, significando
	 *  que a apólice foi incluída com sucesso, o numero da apólice no retorno deve ser 
	 *  o número da apólice incluída.
	 * 
	 * À clase Apolice, deve ser acrescentado (com seus respectivos get/set e presença
	 * dele no construtor) o atributo LocalDate dataInicioVigencia.
	 * 
	 * O número da apólice é igual a:
	 * Se cpfOuCnpj de dados for um CPF
	 *     número da apólice  = ano atual + "000" + cpfOuCnpj + placa  
	 * Se cpfOuCnpj de dados for um CNPJ
	 *     número da apólice  = ano atual + cpfOuCnpj + placa  
	 *     
	 * O valor do prêmio é calculado da seguinte forma
	 * (A) Calcula-se VPA = (3% do valor máximo segurado) 
	 * (B) Calcula-se VPB = 1.2*VPA, se segurado for empresa e indicador de locadora
	 *     for true; ou VPB = VPA, caso contrário.
	 * (C) Calcula-se VPC = VPB - bonus/10.
	 * (D) Calcula-se valor do prêmio = VPC, se VPC > 0; ou valor do prêmio = 0, se 
	 *     VPC <=0.  
	 *      
	 * O valor da franquia é igual a 130% do VPB.
	 * 
	 * Após validar a nulidade de dados e da placa, fazer a busca do veículo por placa.
	 * Se o veículo não existir, realizar todas as valiações pertinentes
	 * Se o veículo existir, realizar as validações de cpf/cnpj e de valor máximo, e a verificação
	 * de apólice já existente (busca de apólice por número).
	 * 
	 * ASSOCIAÇÂO DE VEICULO COM SEGURADOS: buscar segurado empresa por CNPJ OU segura pessoa por CPF. 
	 * Se não for encontrado, retornar mensagem de erro, Se for encontrado, associar ao veículo. 
	 * 
	 * Se o veículo não existir (busca no dao de veículos por placa), ele deve ser incluído 
	 * no dao de veículos com as informações recebidas em dados
	 * Se o veículo existir, (busca no dao de veículos por placa), ele deve ser alterado no 
	 * dao de veículos, mudando-se apenas os segurado empresa e segurado pessoa, cujo cpf ou 
	 * cnpj foi recebido em dados. Estes devem ser atualizados em veículo após validações
	 * de cpf/cnpj e busca deles a partir dos mediators de segurado empresa e de segurado
	 * pessoa.
	 * 
	 * Após todos os dados validados, e o veículo incluído ou alterado, deve-se instanciar
	 * um objeto do tipo Apolice, e incluí-lo no dao de apolice.
	 * 
	 * Após a inclusão bem sucedida da apólice, deve-se bonficar o segurado, se for o caso. 
	 * O segurado, pessoa ou empresa, a depender do cpf ou do cnpj recebido em dados, vai 
	 * ter direito a crédito no bônus se seu cpf ou cnpj não tiver tido sinistro cadastrado
	 * no ano anterior à data de início de vigência da apólice. 
	 * Para inferir isto, deve-se usar um novo método, a ser criado no SinistroDAO, 
	 * public Sinistro[] buscarTodos() e, com o resultado, verificar se existe pelos menos
	 * um sinistro cujo veículo está associado ao cpf ou ao cnpj do segurado da apólice, 
	 * e o ano da data e hora do sinistro seja igual à data de início de vigência da apólice 
	 * menos um. Se existir, não haverá bônus. Caso contrário, deve-se acrescer 30% do valor do
	 * prêmio da apólice ao bônus do segurado pessoa ou segurado empresa, e finalmente alterar o 
	 * segurado pessoa ou segurado empresa no seu respectivo DAO.
	 * 
	 * OBS: TODOS os atributos do tipo BigDecimal devem ser gravados com 02 casas decimais (usar
	 * o método setScale). Se isto não ocorrer, alguns testes vão quebrar.
	 */
	