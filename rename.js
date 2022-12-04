const fs = require('fs');
const path = './assets/player/move';

// Read all file names in the directory
fs.readdirSync(path).forEach((file) => {
    // Replace photoshop "0000" format with "_0" format
    const numbers = file.match(/(\d+)/g)[0];

    // Rename each file
    fs.renameSync(`${path}/${file}`, `${path}/${file.replace(numbers, `_${+numbers}`)}`);
});
