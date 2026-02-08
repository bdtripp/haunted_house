document.addEventListener('DOMContentLoaded', () => {
    const input = document.querySelector('#terminal-input');

    input.addEventListener('keydown', async (e) => {
        if (e.key === 'Enter') {
            const response = await fetch('/api/game/command', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ "input": e.target.value }),
            });

            if (!response.ok) {
                throw new Error(`Request failed: ${response.status}`);
            }

            const text = await response.json();
            console.log(text.output); // or response.text() etc.
        }
    });
});