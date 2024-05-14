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

        // Inicializa contadores para cada categoria de atendimento
        let emergenciaCount = 0;
        let prioritarioICount = 0;
        let prioritarioIICount = 0;
        let geralCount = 0;

        // Conta o número de pacientes em cada categoria de atendimento
        dados.forEach(item => {
            switch (item["Tipo de atendimento"]) {
                case "Emergência":
                    emergenciaCount++;
                    break;
                case "Prioritário I":
                    prioritarioICount++;
                    break;
                case "Prioritário II":
                    prioritarioIICount++;
                    break;
                case "Geral":
                    geralCount++;
                    break;
                default:
                    break;
            }
        });

        // Atualiza os elementos HTML com os contadores
        document.getElementById('emergencia').textContent = emergenciaCount.toString();
        document.getElementById('prioritarioI').textContent = prioritarioICount.toString();
        document.getElementById('prioritarioII').textContent = prioritarioIICount.toString();
        document.getElementById('geral').textContent = geralCount.toString();

        // Calcula e exibe o total de pacientes
        const totalPacientes = emergenciaCount + prioritarioICount + prioritarioIICount + geralCount;
        document.getElementById('total').textContent = totalPacientes.toString();

    } catch (error) {
        console.error('Erro ao atualizar a contagem de pacientes:', error);
    }
}
window.addEventListener('load', atualizarContagemPacientes);