document.addEventListener('DOMContentLoaded', async () => {
    const inputLabel = document.querySelector('label[for="terminal-input"');
    const input = document.querySelector('#terminal-input');
    const outputElement = document.querySelector('#output');
    const newGameBtn = document.querySelector('#new-game-btn');
    const STATUS = {
        RUNNING: 'RUNNING',
        STOPPED: 'STOPPED'
    };

    const startGame = async () => {
        input.disabled = false;
        inputLabel.style.display = 'initial';
        const response = await fetch('api/game/start', {
            method: 'POST'
        });
        const data = await response.json();
        outputElement.textContent = data.output + '\n';
    }
    
    startGame();

    input.addEventListener('keydown', async (e) => {
        const terminal = document.querySelector('#terminal');

        if (e.key === 'Enter') {
            const response = await fetch('/api/game/command', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ 'input': e.target.value }),
            });

            if (!response.ok) {
                throw new Error(`Request failed: ${response.status}`);
            }

            const data = await response.json();

            outputElement.textContent += data.output;
            terminal.scrollTop = terminal.scrollHeight;
            input.value = '';
            if (data.status === STATUS.STOPPED) {
                outputElement.textContent += 'Click "New Game" to play again!';
                input.disabled = true;
                inputLabel.style.display = 'none';
                console.log(inputLabel);
            }
        }
    });

    newGameBtn.addEventListener('click', async () => {
        startGame();
    });
});