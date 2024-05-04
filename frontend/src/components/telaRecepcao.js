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


function selecionarSenha() {
    const displaySenha = document.getElementById('displaySenha');
    const valorSenha = displaySenha.textContent.trim();

    localStorage.setItem('senha', valorSenha);

    window.location.href = '../pages/ficha-atendimento.html';
}

async function buscarConjuntoDeListas() {
    const endpoint = 'http://localhost:8080/listas/main/conjuntolistas';

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
            const numeroNaFila = document.createElement("td");
            numeroNaFila.textContent = item["Número na Fila"];
            novaLinha.appendChild(numeroNaFila);

            const hora = document.createElement("td");
            hora.textContent = item["hora"];
            novaLinha.appendChild(hora);

            const senha = document.createElement("td");
            senha.textContent = item["senha"];
            novaLinha.appendChild(senha);

            const tipoAtendimento = document.createElement("td");
            tipoAtendimento.textContent = item["Tipo de atendimento"];
            novaLinha.appendChild(tipoAtendimento);
            tabelaPacientes.appendChild(novaLinha);
        });

    } catch (erro) {
        console.error("Erro ao exibir o conjunto de listas:", erro);
    }
}
window.addEventListener("load", exibirConjuntoDeListas);

async function exibirPrimeiraSenha(event) {
    if (event) {
        event.preventDefault();
    }
    try {
        const resposta = await fetch('http://localhost:8080/listas/main');

        if (!resposta.ok) {
            throw new Error(`Erro na requisição: ${resposta.status} - ${resposta.statusText}`);
        }

        const dados = await resposta.json();

        if (dados.senha) {
            const displaySenha = document.getElementById('displaySenha');
            displaySenha.textContent = `${dados.senha}`;

            const removerResposta = await fetch('http://localhost:8080/listas/A', {
                method: 'DELETE'
            });

            if (!removerResposta.ok) {
                throw new Error(`Erro ao remover o primeiro paciente: ${removerResposta.status} - ${removerResposta.statusText}`);
            }

            await exibirConjuntoDeListas();
        } else {
            console.log("Sem senha disponível na fila.");
        }

    } catch (erro) {
        console.error('Erro ao exibir a primeira senha:', erro);
    }
}

document.querySelector('.list-group-item-action').addEventListener('click', exibirPrimeiraSenha);