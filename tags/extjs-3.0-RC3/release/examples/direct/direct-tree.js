Ext.onReady(function(){
    Ext.Direct.addProvider(Ext.app.REMOTING_API);

    var tree = new Ext.tree.TreePanel({
        width: 400,
        height: 400,
        autoScroll: true,
        renderTo: document.body,
        root: {
            id: 'root',
            text: 'Root'
        },
        loader: new Ext.tree.TreeLoader({
            directFn: TestAction.getTree
        }),
        fbar: [{
            text: 'Reload root',
            handler: function(){
                tree.getRootNode().reload();
            }
        }]
    });
});