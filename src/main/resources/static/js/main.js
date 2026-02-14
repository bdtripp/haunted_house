document.addEventListener('DOMContentLoaded', async () => {
    const terminal = document.querySelector('#terminal');
    const prompt = document.querySelector('#prompt');
    const input = document.querySelector('#terminal-input');
    const cursor = document.querySelector('#cursor');
    const outputElement = document.querySelector('#output');
    const newGameBtn = document.querySelector('#new-game-btn');
    const STATUS = {
        RUNNING: 'RUNNING',
        STOPPED: 'STOPPED'
    };

    function renderInput() {
        let buffer = input.innerText;
        let cursorPosition = buffer.length - 1;
        input.innerText = '';

        Array.from(buffer).forEach((char, index) => {
            let span = document.createElement('span');
            span.textContent = char;
            if (index === cursorPosition) {
                span.className = 'inverted';
            }
            input.appendChild(span);
        });
    }

    const startGame = async () => {
        input.disabled = false;
        prompt.style.display = 'initial';
        const response = await fetch('api/game/start', {
            method: 'POST'
        });
        const data = await response.json();
        outputElement.textContent = data.output + '\n';
    }
    
    startGame();

    const moveCursorToEnd = () => {
        const range = document.createRange();
        const sel = window.getSelection();

        range.selectNodeContents(input);
        range.collapse(false); // collapse to end
        sel.removeAllRanges();
        sel.addRange(range);
    };

    terminal.addEventListener('click', () => {
        input.focus();
        moveCursorToEnd();
    });

    input.addEventListener("focus", () => {
        moveCursorToEnd();
    });

    input.addEventListener('keydown', async (e) => {
        if(e.key.length === 1) {
            input.innerText = e.key;
            renderInput();
            e.preventDefault;
        }

        if (e.key === 'Enter') {
            const response = await fetch('/api/game/command', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ 'input': e.target.textContent }),
            });

            if (!response.ok) {
                throw new Error(`Request failed: ${response.status}`);
            }

            const data = await response.json();

            outputElement.textContent += data.output;
            terminal.scrollTop = terminal.scrollHeight;
            input.innerText = '';
            if (data.status === STATUS.STOPPED) {
                outputElement.textContent += 'Click "New Game" to play again!';
                input.disabled = true;
                prompt.style.display = 'none';
                console.log(prompt);
            }
        }
    });

    newGameBtn.addEventListener('click', async () => {
        startGame();
    });
});