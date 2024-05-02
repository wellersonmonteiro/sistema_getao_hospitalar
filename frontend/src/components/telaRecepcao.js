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

document.addEventListener("DOMContentLoaded", function() {
    // URL do endpoint para obter a primeira senha por ordem de prioridade
    const url = "http://localhost:8080/listas/main";

    // Faça uma solicitação HTTP ao endpoint usando fetch
    fetch(url, {
        method: "GET", // Ou "GET" conforme necessário
        headers: {
            "Content-Type": "application/json" // Se necessário para POST ou PUT
        }
    })
        .then(response => response.json()) // Converte a resposta para JSON
        .then(data => {
            // `data` contém a resposta do servidor com a senha
            const senha = data.senha;

            // Encontre a `div` com o ID `displaySenha`
            const displaySenha = document.getElementById('displaySenha');

            // Atualize o texto da `div` com a senha recebida
            displaySenha.textContent = senha;
        })
        .catch(error => {
            console.error("Erro ao obter a primeira senha:", error);
        });
});

function obterPrimeiraSenha() {
    // URL da API
    const url = 'http://localhost:8080/listas/main'; // Certifique-se de que a URL está correta

    // Fazer a requisição GET
    fetch(url)
        .then(response => {
            // Verificar se a resposta está ok
            if (!response.ok) {
                throw new Error('Erro na resposta da API');
            }
            // Converter a resposta para JSON
            return response.json();
        })
        .then(data => {
            // Adicione o log aqui para verificar os dados recebidos
            console.log('Dados recebidos da API:', data);

            // Atualizar o elemento HTML com a primeira senha
            const displaySenha = document.getElementById('displaySenha');
            if (data.senha) {
                displaySenha.textContent = `Senha: ${data.senha}`;
            } else {
                displaySenha.textContent = 'Sem atendimento';
            }
        })
        .catch(error => {
            console.error('Erro ao obter os dados:', error);
            // Tratar erro, se necessário
        });
}

window.addEventListener('load', () => {
    obterPrimeiraSenha();
});
