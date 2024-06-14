function formatarHorario(date) {

    const horas = date.getHours().toString().padStart(2, '0');
    const minutos = date.getMinutes().toString().padStart(2, '0');
    const segundos = date.getSeconds().toString().padStart(2, '0');

    return `${horas}:${minutos}:${segundos}`;
}

function atualizarHorario() {
    const elementoHorario = document.getElementById('horarioAtual');

    const now = new Date();

    const horarioFormatado = formatarHorario(now);

    elementoHorario.textContent = horarioFormatado;
}
setInterval(atualizarHorario, 1000);





async function buscarConjuntoDeListas() {
    const endpoint = 'http://localhost:8080/consultorio/main/conjuntolistas';
    try {
        const resposta = await fetch(endpoint);

        if (!resposta.ok) {
            throw new Error(`Erro na requisição: ${resposta.status} - ${resposta.statusText}`);
        }

        const dados = await resposta.json();
        console.log('Conjunto de listas recebidas:', dados);
        return dados;

    } catch (erro) {
        console.error('Erro ao buscar o conjunto de listas:', erro);
        throw erro;
    }
}

async function exibirConjuntoDeListas() {
    try {
        const conjuntoDeListas = await buscarConjuntoDeListas();
        console.log("Dados recebidos:", conjuntoDeListas);

        const tabelaPacientes = document.querySelector("#pacientesTable tbody");
        tabelaPacientes.innerHTML = "";

        conjuntoDeListas.forEach(item => {
            const novaLinha = document.createElement("tr");
            novaLinha.innerHTML = `
                <td>${item["Número na Fila"]}</td>
                <td>${item["nome"]}</td>
                <td>${item["senhaCor"]}</td>
                <td>${item["senha"]}</td>
                <td>${item["Tipo de atendimento"]}</td>`;
            tabelaPacientes.appendChild(novaLinha);
        });

    } catch (erro) {
        console.error("Erro ao exibir o conjunto de listas:", erro);
    }
}
window.addEventListener("load", exibirConjuntoDeListas);

async function atualizarContagemPacientes() {
    try {
        const response = await fetch('http://localhost:8080/consultorio/main/conjuntolistas');

        if (!response.ok) {
            throw new Error(`Erro na requisição: ${response.status} - ${response.statusText}`);
        }

        const dados = await response.json();
        console.log('JSON recebido:', JSON.stringify(dados, null, 2));

        let emergenciaCount = 0;
        let prioritarioICount = 0;
        let prioritarioIICount = 0;
        let geralCount = 0;

        dados.forEach(item => {
            switch (item["senhaCor"]) {
                case "Vermelha":
                    emergenciaCount++;
                    break;
                case "Amarela":
                    prioritarioICount++;
                    break;
                case "Verde":
                    prioritarioIICount++;
                    break;
                case "Azul":
                    geralCount++;
                    break;
                default:
                    break;
            }
        });

        document.getElementById('vermelha').textContent = emergenciaCount.toString();
        document.getElementById('amarela').textContent = prioritarioICount.toString();
        document.getElementById('verde').textContent = prioritarioIICount.toString();
        document.getElementById('azul').textContent = geralCount.toString();

        const totalPacientes = emergenciaCount + prioritarioICount + prioritarioIICount + geralCount;
        document.getElementById('total').textContent = totalPacientes.toString();

    } catch (error) {
        console.error('Erro ao atualizar a contagem de pacientes:', error);
    }
}
window.addEventListener('load', atualizarContagemPacientes);


async function solicitarDados() {
    try {
        const resposta = await fetch('http://localhost:8080/consultorio/main', {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        });

        if (!resposta.ok) {
            throw new Error(`Erro na requisição: ${resposta.status} - ${resposta.statusText}`);
        }

        // Recarregar a página imediatamente após a resposta do backend ser recebida com sucesso
        window.location.reload();
    } catch (erro) {
        console.error('Erro ao solicitar dados:', erro);
    }
}

// Adicionar evento de clique ao botão
document.getElementById('btnSolicitarDados').addEventListener('click', solicitarDados);


document.getElementById('filtroAtendimento').addEventListener('change', function() {
    var filtro = this.value;
    var tabela = document.getElementById('pacientesTable');
    var linhas = tabela.getElementsByTagName('tbody')[0].getElementsByTagName('tr');

    for (var i = 0; i < linhas.length; i++) {
        var celulaTipoAtendimento = linhas[i].getElementsByTagName('td')[4];
        if (celulaTipoAtendimento) {
            var tipoAtendimento = celulaTipoAtendimento.textContent || celulaTipoAtendimento.innerText;
            if (filtro === 'todos' || tipoAtendimento === filtro) {
                linhas[i].style.display = '';
            } else {
                linhas[i].style.display = 'none';
            }
        }
    }
});

