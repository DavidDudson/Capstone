require.config({
    paths: {
        'jQuery': 'jquery-1.10.2',
        'bootstrap': 'bootstrap-min',
        'underscore':'underscore-min',
        'bootbox':'bootbox-min',
        'jQuery-ui':'jquery-ui-1.10.3.custom.min.js'
    }
});

require(["tester"], function(m) {
    console.log("loaded test");
});
