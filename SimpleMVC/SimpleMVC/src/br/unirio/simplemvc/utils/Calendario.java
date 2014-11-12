package br.unirio.simplemvc.utils;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.DateTimeZone;
import org.joda.time.Days;

public class Calendario
{
	private DateTime[] feriados;
    private int anoInicial;
    private int anoFinal;
    private int[] listaFeriadosAno;

    /**
     * Inicializa o calendário
     * 
     * @param feriados		Vetor de feriados
     */
    public Calendario(DateTime[] feriados)
    {
        this.feriados = feriados;
        this.anoInicial = 0;
        this.anoFinal = 0;
        carregaListaFeriadosAno();
    }

    /**
     * Número de feriados registrados no calendário
     */
    public int pegaNumeroFeriados()
    {
        return feriados.length;
    }

    /**
     * Retorna um feriado, dado seu índice
     * 
     * @param indice		Índice do feriado desejado
     */
    public DateTime pegaFeriadoIndice(int indice)
    {
        return (indice >= 0 && indice < feriados.length) ? feriados[indice] : null;
    }

    /**
     * Carrega a lista de feriados do ano
     */
    private void carregaListaFeriadosAno()
    {
        int nferiados = pegaNumeroFeriados();

        DateTime dataInicio = pegaFeriadoIndice(0);
        anoInicial = dataInicio.getYear();

        DateTime dataFinal = pegaFeriadoIndice(nferiados - 1);
        anoFinal = dataFinal.getYear();

        int tamanho = anoFinal - anoInicial + 1;
        listaFeriadosAno = new int[tamanho];

        for (int i = 0; i < nferiados; i++)
        {
            DateTime data = pegaFeriadoIndice(i);
            int indice = data.getYear() - anoInicial;
            listaFeriadosAno[indice]++;
        }

        int acumulado = 0;

        for (int i = 0; i < tamanho; i++)
        {
            int valor = listaFeriadosAno[i];
            listaFeriadosAno[i] = acumulado;
            acumulado += valor;
        }
    }

    /**
     * Verifica se uma data é feriado, retornando os índices dos feriados próximos à data
     * 
     * @param date	Data do feriado desejado
     */
    private ResultadoAnaliseFeriado VerificaFeriadoInterno(DateTime date)
    {
        int nferiados = feriados.length;
        int anoAtual = date.getYear();

        if (nferiados == 0 || anoAtual < anoInicial || anoAtual > anoFinal)
            return new ResultadoAnaliseFeriado(-1, -1, false);
        
        int indiceAno = anoAtual - anoInicial;

        int low = listaFeriadosAno[indiceAno];
        int med = low;
        int hi = (anoAtual == anoFinal) ? nferiados - 1 : listaFeriadosAno[indiceAno + 1];

        while (low <= hi)
        {
            med = (low + hi) / 2;
            DateTime feriado = feriados[med];
            int diferenca = -Days.daysBetween(feriado, date).getDays();

            if (diferenca == 0)
                return new ResultadoAnaliseFeriado(med, true);

            if (diferenca > 0)
                hi = med - 1;
            else
                low = med + 1;
        }

        DateTime feriado = feriados[med];
        int diferenca = -Days.daysBetween(feriado, date).getDays();
        int proximo = med;
        int anterior = med;

        if (diferenca < 0)
        {
            if (proximo >= nferiados - 1)
                return new ResultadoAnaliseFeriado(proximo, -1, false);

            while (diferenca < 0 && proximo < nferiados-1)
            {
                feriado = feriados[++proximo];
                diferenca = -Days.daysBetween(feriado, date).getDays();
            }

            return new ResultadoAnaliseFeriado(proximo-1, proximo, false);
        }
        else
        {
            if (proximo == 0)
                return new ResultadoAnaliseFeriado(-1, 0, false);

            while (diferenca > 0 && anterior > 0)
            {
                feriado = feriados[--anterior];
                diferenca = -Days.daysBetween(feriado, date).getDays();
            }

            return new ResultadoAnaliseFeriado(anterior, anterior+1, false);
        }
    }

    /**
     * Calcula uma data após um determinado número de dias úteis
     * 
     * @param data		Data de referência
     * @param dias		Número de dias úteis que serão adicionados
     */
    public DateTime dataAposDiasUteis(DateTime data, int dias)
    {
        if (dias == 0)
            return data;

        if (dias > 0)
            return dataAposDiasUteisFuturo(data, dias);

        return dataAposDiasUteisPassado(data, dias);
    }

    /**
     * Calcula uma data após um determinado número de dias úteis no futuro
     * 
     * @param data		Data de referência
     * @param dias		Número de dias úteis que serão adicionados
     */
    private DateTime dataAposDiasUteisFuturo(DateTime data, int dias)
    {
        if (dias == 1)
        {
            data = data.plusDays(dias);

            while (!verificaDiaUtil(data))
                data = data.plusDays(dias);

            return data;
        }

        int dia = (int)data.getDayOfWeek() + 1;
    	ResultadoAnaliseFeriado resultado = VerificaFeriadoInterno (data); 
        int indiceFeriado = resultado.verificaEncontrou() ? resultado.pegaProximo()+1 : resultado.pegaProximo();
        DateTime proximoFeriado = (indiceFeriado != -1) ? feriados[indiceFeriado] : null;

        if (proximoFeriado != null && proximoFeriado.isBefore(data))
            proximoFeriado = null;

        DateTime proximoSabado = data.plusDays(7 - dia);
        DateTime proximoDomingo = data.plusDays(8 - dia);
        DateTime proximoDiaNaoUtil = (proximoFeriado != null && (proximoFeriado.isBefore(proximoSabado) || proximoFeriado.isEqual(proximoSabado))) ? proximoFeriado : proximoSabado;
        proximoDiaNaoUtil = proximoDiaNaoUtil.isAfter(proximoDomingo) ? proximoDomingo : proximoDiaNaoUtil;

        while (dias != 0)
        {
            int passo = Math.min(dias, -Days.daysBetween(proximoDiaNaoUtil, data).getDays() - 1);

            if (passo < 0)
                passo = 0;

            data = data.plusDays(passo);
            dias = dias - passo;

            if (dias != 0)
            {
                data = proximoDiaNaoUtil;

                if (proximoDiaNaoUtil == proximoSabado)
                    proximoSabado = proximoDiaNaoUtil.plusDays(7);

                if (proximoDiaNaoUtil == proximoDomingo)
                    proximoDomingo = proximoDiaNaoUtil.plusDays(7);

                if (proximoDiaNaoUtil == proximoFeriado)
                    proximoFeriado = (indiceFeriado < feriados.length-1) ? feriados[++indiceFeriado] : null;

                if (proximoFeriado != null && proximoFeriado.isAfter(proximoDiaNaoUtil))
                    proximoDiaNaoUtil = proximoFeriado.isAfter(proximoSabado) ? proximoSabado : proximoFeriado;
                else
                    proximoDiaNaoUtil = proximoSabado;

                proximoDiaNaoUtil = proximoDiaNaoUtil.isAfter(proximoDomingo) ? proximoDomingo : proximoDiaNaoUtil;
            }
        }

        return data;
    }

    /**
     * Calcula uma data após um determinado número de dias úteis no passado
     * 
     * @param data		Data de referência
     * @param dias		Número de dias úteis que serão adicionados
     */
    private DateTime dataAposDiasUteisPassado(DateTime data, int dias)
    {
        if (dias == -1)
        {
            data = data.plusDays(dias);

            while (!verificaDiaUtil(data))
                data = data.plusDays(dias);

            return data;
        }

        int dia = (data.getDayOfWeek() == 7) ? 1 : data.getDayOfWeek() + 1;
    	ResultadoAnaliseFeriado resultado = VerificaFeriadoInterno (data);
        int indiceFeriado = resultado.verificaEncontrou() ? resultado.pegaAnterior()-1 : resultado.pegaAnterior();
        DateTime proximoFeriado = (indiceFeriado == -1) ? null : feriados[indiceFeriado];

        DateTime proximoSabado = data.minusDays(dia);
        DateTime proximoDomingo = (dia != 1) ? data.plusDays(1 - dia) : data.minusDays(7);
        DateTime proximoDiaNaoUtil = (proximoFeriado != null && proximoFeriado.isAfter(proximoSabado)) ? proximoFeriado : proximoSabado;
        proximoDiaNaoUtil = proximoDiaNaoUtil.isAfter(proximoDomingo) ? proximoDiaNaoUtil : proximoDomingo;

        while (dias != 0)
        {
            int passo = Math.max(dias, -Days.daysBetween(proximoDiaNaoUtil, data).getDays() + 1);

            if (passo > 0)
                passo = 0;

            data = data.plusDays(passo);
            dias = dias - passo;

            if (dias != 0)
            {
                data = proximoDiaNaoUtil;

                if (proximoDiaNaoUtil == proximoSabado)
                    proximoSabado = proximoDiaNaoUtil.minusDays(7);

                if (proximoDiaNaoUtil == proximoDomingo)
                    proximoDomingo = proximoDiaNaoUtil.minusDays(7);

                if (proximoDiaNaoUtil == proximoFeriado)
                    proximoFeriado = (indiceFeriado > 0) ? feriados[--indiceFeriado] : null;

                proximoDiaNaoUtil = (proximoFeriado != null && proximoFeriado.isAfter(proximoSabado)) ? proximoFeriado : proximoSabado;
                proximoDiaNaoUtil = proximoDiaNaoUtil.isAfter(proximoDomingo) ? proximoDiaNaoUtil : proximoDomingo;
            }
        }

        return data;
    }

    /**
     * Calcula o número de dias úteis entre duas datas
     * 
     * @param dataInicio		Data de início do período desejado
     * @param dataFinal			Data de término do período desejado
     */
    public int diasUteisEntreDatas(DateTime dataInicio, DateTime dataFinal)
    {
        // Trunca as datas - Somamos para considerar fechamento contra fechamento,
        // p.e., sexta a sábado (0 dias úteis), domindo a segunda (1 dia útil)
        dataInicio = dataInicio.plusDays(1);
        dataFinal = dataFinal.plusDays(1);

        // Calcula o dia da semana em que ocorre a primeira data
        while (!verificaDiaUtil(dataInicio))
            dataInicio = dataInicio.plusDays(1);

        // Calcula o dia da semana em que ocorre a segunda data
        while (!verificaDiaUtil(dataFinal))
        	dataFinal = dataFinal.plusDays(1);

        // Calcula o numero de dias uteis entre as duas datas
        if (dataInicio.isAfter(dataFinal))
            return 0;

        // Pega o primeiro feriado apos a primeira data
        int primFeriado = VerificaFeriadoInterno(dataInicio).pegaProximo();

        // Pega o ultimo feriado antes da segunda data
        int ultFeriado = VerificaFeriadoInterno(dataFinal).pegaAnterior();

        // Calcula o numero de feriados no periodo
        int nferiados = 0;

        if (ultFeriado >= primFeriado && ultFeriado >= 0)
        {
            primFeriado = Math.max(primFeriado, 0);

            for (int i = primFeriado; i <= ultFeriado; i++)
            {
                DateTime feriado = pegaFeriadoIndice(i);
                int dw = feriado.getDayOfWeek();

                if (dw != DateTimeConstants.SATURDAY && dw != DateTimeConstants.SUNDAY)
                    nferiados++;
            }
        }

        // Calcula o número de dias úteis no período (se houve virada de semana, acrescenta 5 dias)
        int dia_semana1 = dataInicio.getDayOfWeek();
        int dia_semana2 = dataFinal.getDayOfWeek();
        int dias = (-Days.daysBetween(dataFinal, dataInicio).getDays() / 7) * 5;

        if (dia_semana2 < dia_semana1)
            dias += 5;

        return (dias + (dia_semana2 - dia_semana1 + 1) - nferiados - 1);
    }

    /**
     * Verifica se uma data é dia útil
     * 
     * @param data		Data desejada
     */
    public boolean verificaDiaUtil(DateTime data)
    {
        int dia = data.getDayOfWeek();

        if (dia == DateTimeConstants.SATURDAY || dia == DateTimeConstants.SUNDAY)
            return false;

        return !verificaFeriado(data);
    }

    /**
     * Verifica se uma data é feriado
     * 
     * @param data		Data desejada
     */
    public boolean verificaFeriado(DateTime data)
    {
        return VerificaFeriadoInterno(data).verificaEncontrou();
    }

    /**
     * Calcula o primeiro dia útil da semana de uma data
     * 
     * @param data		Data desejada
     * @return
     */
    public DateTime calculaPrimeiroDiaUtilSemana(DateTime data)
    {
        if (data.getDayOfWeek() == DateTimeConstants.SUNDAY)
            data = data.minusDays(1);

        data = data.plusDays(DateTimeConstants.MONDAY - data.getDayOfWeek());

        while (!verificaDiaUtil(data))
            data = data.plusDays(1);

        return data;
    }
    
    /**
     * Cria uma data a partir dos seus componentes
     */
    public static DateTime cria(int ano, int mes, int dia)
    {
		return new DateTime(ano, mes, dia, 0, 0, 0, 0, DateTimeZone.UTC);
    }

    /**
     * Calcula o primeiro dia útil do mês de uma data
     * 
     * @param data		Data desejada
     */
    public DateTime calculaPrimeiroDiaUtilMes(DateTime data)
    {
        data = cria(data.getYear(), data.getMonthOfYear(), 1);

        while (!verificaDiaUtil(data))
            data = data.plusDays(1);

        return data;
    }

    /**
     * Calcula o primeiro dia útil do trimestre de uma data
     * 
     * @param data			Data desejada
     */
    public DateTime calculaPrimeiroDiaUtilTrimestre(DateTime data)
    {
        int mes = data.getMonthOfYear();
        mes = ((mes - 1) / 3) * 3 + 1;
        data = cria(data.getYear(), mes, 1);

        while (!verificaDiaUtil(data))
            data = data.plusDays(1);

        return data;
    }

    /**
     * Calcula o primeiro dia útil do semestre de uma data
     * 
     * @param data		Data desejada
     */
    public DateTime calculaPrimeiroDiaUtilSemestre(DateTime data)
    {
        int mes = data.getMonthOfYear();
        mes = ((mes - 1) / 6) * 6 + 1;
        data = cria(data.getYear(), mes, 1);

        while (!verificaDiaUtil(data))
            data = data.plusDays(1);

        return data;
    }

    /**
     * Calcula o primeiro dia útil do ano de uma data
     * 
     * @param data		Data desejada
     */
    public DateTime calculaPrimeiroDiaUtilAno(DateTime data)
    {
        data = cria(data.getYear(), 1, 1);

        while (!verificaDiaUtil(data))
            data = data.plusDays(1);

        return data;
    }

    /**
     * Calcula o último dia útil da semana de uma data
     * 
     * @param data		Data desejada
     */
    public DateTime calculaUltimoDiaUtilSemana(DateTime data)
    {
    	int dia = data.getDayOfWeek() % 7 + 1;
    	int dias = DateTimeConstants.FRIDAY + 1 - dia;
        data = data.plusDays(dias);

        while (!verificaDiaUtil(data))
            data = data.minusDays(1);

        return data;
    }

    /**
     * Retorna o número de dias em um mês
     * 
     * @param year		Ano do mês desejado
     * @param month		Mês desejado
     */
    public int daysInMonth(int year, int month) 
    {
    	DateTime dateTime = cria(year, month, 14);
    	return dateTime.dayOfMonth().getMaximumValue();
	}
    
    /**
     * Calcula o último dia útil do mês de uma data
     * 
     * @param data		Data desejada
     */
    public DateTime calculaUltimoDiaUtilMes(DateTime data)
    {
        int mes = data.getMonthOfYear(), ano = data.getYear();
        data = cria(ano, mes, daysInMonth(ano, mes));

        while (!verificaDiaUtil(data))
            data = data.minusDays(1);

        return data;
    }

    /**
     * Calcula o último dia útil do trimestre de uma data
     * 
     * @param data		Data desejada
     */
    public DateTime calculaUltimoDiaUtilTrimestre(DateTime data)
    {
        int mes = data.getMonthOfYear(), ano = data.getYear();
        mes = ((mes - 1) / 3) * 3 + 1;
        data = cria(ano, mes + 2, daysInMonth(ano, mes + 2));

        while (!verificaDiaUtil(data))
            data = data.minusDays(1);

        return data;
    }

    /**
     * Calcula o último dia útil do semestre de uma data
     * 
     * @param data		Data desejada
     */
    public DateTime calculaUltimoDiaUtilSemestre(DateTime data)
    {
        int mes = data.getMonthOfYear(), ano = data.getYear();
        mes = ((mes - 1) / 6) * 6 + 1;
        data = cria(ano, mes + 5, daysInMonth(ano, mes + 5));

        while (!verificaDiaUtil(data))
            data = data.minusDays(1);

        return data;
    }

    /**
     * Calcula o último dia útil do ano de uma data
     * 
     * @param data		Data desejada
     */
    public DateTime calculaUltimoDiaUtilAno(DateTime data)
    {
        data = cria(data.getYear(), 12, 31);

        while (!verificaDiaUtil(data))
            data = data.minusDays(1);

        return data;
    }
}

class ResultadoAnaliseFeriado
{
	private int anterior;
	private int proximo;
	private boolean encontrou;

	public ResultadoAnaliseFeriado(int posicao, boolean encontrou)
	{
		this.anterior = posicao;
		this.proximo = posicao;
		this.encontrou = encontrou;
	}
	
	public ResultadoAnaliseFeriado(int anterior, int proximo, boolean encontrou)
	{
		this.anterior = anterior;
		this.proximo = proximo;
		this.encontrou = encontrou;
	}
	
	public boolean verificaEncontrou()
	{
		return encontrou;
	}
	
	public int pegaAnterior()
	{
		return anterior;
	}
	
	public int pegaProximo()
	{
		return proximo;
	}
}