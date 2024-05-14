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
    const endpoint = 'http://localhost:8080/listatriagem/main/conjuntolistas';
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
                <td>${item["hora"]}</td>
                <td>${item["senha"]}</td>
                <td>${item["Tipo de atendimento"]}</td>`;
            tabelaPacientes.appendChild(novaLinha);
        });

    } catch (erro) {
        console.error("Erro ao exibir o conjunto de listas:", erro);
    }
}
window.addEventListener("load", exibirConjuntoDeListas);


async function exibirPrimeiraSenha() {
    try {
        const conjuntoDeListas = await buscarConjuntoDeListas();

        if (conjuntoDeListas.length > 0) {
            const primeiroPaciente = conjuntoDeListas[0];
            const senhaDoPrimeiroPaciente = primeiroPaciente.senha;

            const displaySenha = document.getElementById("displaySenha");
            displaySenha.textContent = senhaDoPrimeiroPaciente;
        } else {
            const displaySenha = document.getElementById("displaySenha");
            displaySenha.textContent = "Sem senha disponível na fila.";
        }

    } catch (erro) {
        console.error("Erro ao exibir a primeira senha:", erro);
        const displaySenha = document.getElementById("displaySenha");
        displaySenha.textContent = "Erro ao obter a primeira senha.";
    }
}
window.addEventListener("load", exibirPrimeiraSenha);

async function exibirSegundaSenha() {
    try {
        const resposta = await fetch('http://localhost:8080/listatriagem/main');

        if (!resposta.ok) {
            throw new Error(`Erro na requisição: ${resposta.status} - ${resposta.statusText}`);
        }
        const dados = await resposta.json();
        if (dados && dados.senha) {
            const displaySenha = document.getElementById('displaySenha');
            displaySenha.textContent = dados.senha;
        } else {
            console.log("Sem senha disponível na fila.");
            const displaySenha = document.getElementById('displaySenha');
            displaySenha.textContent = "Sem senha disponível na fila.";
        }
        window.location.reload();

    } catch (erro) {
        // Trate qualquer erro que possa ocorrer
        console.error('Erro ao exibir a primeira senha:', erro);
        const displaySenha = document.getElementById('displaySenha');
        displaySenha.textContent = "Erro ao obter a primeira senha.";
    }
}
document.getElementById('chamarProximoPaciente').addEventListener('click', exibirSegundaSenha);


function anunciarSenha() {
    const displaySenha = document.getElementById('displaySenha');
    let senha = displaySenha.textContent.trim();
    senha = "Senha " + senha;

    const fala = new SpeechSynthesisUtterance(senha);

    fala.rate = 0.6; // Taxa de fala (1 é a taxa normal)
    fala.pitch = 0.6; // Tom (1 é o tom normal)
    fala.volume = 1; // Volume (1 é o volume máximo)

    speechSynthesis.speak(fala);
}

async function mostrarTipoAtendimentoPrimeiroElemento() {
    try {
        const url = 'http://localhost:8080/listatriagem/main/conjuntolistas';
        const response = await fetch(url);

        if (!response.ok) {
            throw new Error('Erro ao buscar os dados JSON');
        }

        const dados = await response.json();

        if (dados.length > 0) {
            const tipoAtendimento = dados[0]['Tipo de atendimento'];
            const displayPrioridade = document.getElementById('displayPrioridade');

            displayPrioridade.textContent = tipoAtendimento;
        } else {
            console.error('A fila está vazia');
        }
    } catch (error) {
        console.error('Erro ao buscar os dados ou processar a resposta:', error);
    }
}
mostrarTipoAtendimentoPrimeiroElemento();

async function mostrarNomePrimeiroElemento() {
    try {
        const url = 'http://localhost:8080/listatriagem/main/conjuntolistas';
        const response = await fetch(url);

        if (!response.ok) {
            throw new Error('Erro ao buscar os dados JSON');
        }

        const dados = await response.json();

        if (dados.length > 0) {
            const nome = dados[0]['nome'];
            const displayNome = document.getElementById('displayNome');

            displayNome.textContent = nome;
        } else {
            console.error('A fila está vazia');
        }
    } catch (error) {
        console.error('Erro ao buscar os dados ou processar a resposta:', error);
    }
}
mostrarNomePrimeiroElemento();

function selecionarSenha() {

    const displaySenha = document.getElementById('displaySenha');
    const valorSenha = displaySenha.textContent.trim();

    const displayPrioridade = document.getElementById('displayPrioridade');
    const prioridadeTexto = displayPrioridade.textContent.trim();

    const displayNome = document.getElementById('displayNome');
    const valorNome = displayNome.textContent.trim();

    localStorage.setItem('Tipo de atendimento', prioridadeTexto);
    localStorage.setItem('senha', valorSenha);
    localStorage.setItem('nome', valorNome);

    window.location.href = '../pages/ficha-atendimento-triagem.html';
}