document.addEventListener('DOMContentLoaded', async () => {
    const input = document.querySelector('#terminal-input');
    const outputElement = document.querySelector('#output');
    const newGameBtn = document.querySelector('#new-game-btn');
    
    startGame(outputElement);

    input.addEventListener('keydown', async (e) => {
        const terminal = document.querySelector('#terminal');

        if (e.key === 'Enter') {
            const response = await fetch('/api/game/command', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ "input": e.target.value }),
            });

            if (!response.ok) {
                throw new Error(`Request failed: ${response.status}`);
            }

            const data = await response.json();

            outputElement.textContent += data.output;
            terminal.scrollTop = terminal.scrollHeight;
            input.value = '';
        }
    });

    newGameBtn.addEventListener('click', async () => {
        startGame(outputElement);
    });
});

const startGame = async (outputElement) => {
    const response = await fetch('api/game/start', {
        method: 'POST'
    });
    const data = await response.json();
    outputElement.textContent = data.output + "\n";
}