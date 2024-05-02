document.addEventListener("DOMContentLoaded", function() {

    const buttonIds = {
        botaoSolicitarSenhaA: "http://localhost:8080/listas/A",
        botaoSolicitarSenhaP: "http://localhost:8080/listas/P",
        botaoSolicitarSenhaPP: "http://localhost:8080/listas/PP",
        botaoSolicitarSenhaB: "http://localhost:8080/listas/B"
    };

    function handleButtonClick(event) {
        event.preventDefault();
        const buttonId = event.target.id;
        const url = buttonIds[buttonId];

        fetch(url, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            }
        })
            .then(response => response.json())
            .then(data => {
                const senhaDisplay = `${data.senha}`;
                const displaySenha = document.getElementById("displaySenha");
                displaySenha.textContent = senhaDisplay;
            })
            .catch(error => {
                console.error("Erro ao buscar a senha:", error);
            });
    }

    for (const buttonId in buttonIds) {
        const button = document.getElementById(buttonId);
        if (button) {
            button.addEventListener("click", handleButtonClick);
        }
    }
});


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
