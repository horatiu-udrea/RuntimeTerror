import MainScene from "./scene";

function component() {
    const element = document.createElement('div');
  
    // Lodash, currently included via a script, is required for this line to work
    // element.innerHTML = _.join(['Hello', 'webpack'], ' ');
    var scene = new MainScene();
    element.innerHTML = scene;
    return element;
  }
  
  document.body.appendChild(component());