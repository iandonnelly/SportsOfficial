Pebble.addEventListener('ready', function(e) {
    console.log('JavaScript app ready and running!');
  }
);

Pebble.addEventListener('showConfiguration', function(e) {
  // Show config page
  console.log('Showing config page');
  Pebble.openURL('https://dl.dropboxusercontent.com/u/31585964/SportsOfficialConfig.html');
});

Pebble.addEventListener("webviewclosed", function(e) {
    //Get JSON dictionary
    console.log("Webview closed");
    console.log(e.response);
    var config = JSON.parse(decodeURIComponent(e.response));
    console.log("Config: " + JSON.stringify(config));
    
    var single = parseInt(config.single);
    var long = parseInt(config.long);
    var double = parseInt(config.double);
  
    //Send to Pebble
    Pebble.sendAppMessage(
      {"SINGLE_CLICK_KEY": single, "LONG_CLICK_KEY": long, "DOUBLE_CLICK_KEY": double},
      function(e) {
        console.log("Sending settings data...");
      },
      function(e) {
        console.log("Settings feedback failed!");
      }
    );
  }
);