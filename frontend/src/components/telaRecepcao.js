function formatarHorario(date) {

    const horas = date.getHours().toString().padStart(2, '0');
    const minutos = date.getMinutes().toString().padStart(2, '0');
    const segundos = date.getSeconds().toString().padStart(2, '0');

    return `${horas}:${minutos}:${segundos}`;
}

function atualizarHorario() {
    // Obtenha o elemento HTML onde o horário será exibido
    const elementoHorario = document.getElementById('horarioAtual');

    // Obtenha a data e hora atuais
    const now = new Date();

    // Formate o horário
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

// Função para buscar o conjunto de listas do endpoint `/listas/main/conjuntolistas`
async function buscarConjuntoDeListas() {
    // Define o endpoint da API
    const endpoint = 'http://localhost:8080/listas/main/conjuntolistas';

    try {
        // Faz a requisição GET para a API
        const resposta = await fetch(endpoint);

        // Verifica se a resposta é bem-sucedida
        if (!resposta.ok) {
            throw new Error(`Erro na requisição: ${resposta.status} - ${resposta.statusText}`);
        }

        // Converte a resposta para JSON
        const dados = await resposta.json();

        // Exibe os dados recebidos para verificar
        console.log('Conjunto de listas recebidas:', dados);

        // Retorna os dados recebidos
        return dados;

    } catch (erro) {
        console.error('Erro ao buscar o conjunto de listas:', erro);
        throw erro;
    }
}

async function exibirConjuntoDeListas() {
    try {
        // Busca o conjunto de listas
        const conjuntoDeListas = await buscarConjuntoDeListas();

        // Verifique os dados recebidos no console para depuração
        console.log("Dados recebidos:", conjuntoDeListas);

        // Seleciona o tbody da tabela de pacientes
        const tabelaPacientes = document.querySelector("#pacientesTable tbody");

        // Limpa a tabela antes de adicionar novas linhas
        tabelaPacientes.innerHTML = "";

        // Itera sobre o conjunto de listas para criar as linhas da tabela
        conjuntoDeListas.forEach(item => {
            // Cria uma nova linha da tabela
            const novaLinha = document.createElement("tr");

            // Cria células para "Número na Fila", "hora", "senha" e "Tipo de Atendimento"
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

            // Adiciona a nova linha ao tbody da tabela
            tabelaPacientes.appendChild(novaLinha);
        });

    } catch (erro) {
        console.error("Erro ao exibir o conjunto de listas:", erro);
    }
}

// Chama a função para exibir o conjunto de listas ao carregar a página
window.addEventListener("load", exibirConjuntoDeListas);
