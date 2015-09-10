require.config({
    paths: {
        'jQuery': 'jquery-1.10.2.min',
        'bootstrap': 'bootstrap.min',
        'ace': 'ace',
        'underscore':'underscore-min',
        'bootbox':'bootbox.min'
    }
});

require(["bruyere-editor"], function(m) {
    console.log("loaded editor");
});
