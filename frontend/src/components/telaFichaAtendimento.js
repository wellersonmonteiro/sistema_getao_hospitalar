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

    // Atualize o texto do elemento HTML com o horário formatado
    elementoHorario.textContent = horarioFormatado;
}

// Atualize o horário a cada segundo (1000 milissegundos)
setInterval(atualizarHorario, 1000);



document.addEventListener("DOMContentLoaded", function() {
    // Obtenha a senha de localStorage ou sessionStorage
    const senha = localStorage.getItem('senha');

    // Se a senha existe, insira o valor no input com ID 'campo1'
    if (senha !== null) {
        const inputCampo1 = document.getElementById('campo1');
        inputCampo1.value = senha;
    }
});
