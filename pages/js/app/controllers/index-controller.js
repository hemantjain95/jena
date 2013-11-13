/** Controller for the main index.html page */
define(
  function( require ) {
    var Marionette = require( "marionette" ),
        Backbone = require( "backbone" ),
        _ = require( "underscore" ),
        fui = require( "fui" ),
        qonsole = require( "lib/qonsole" );

    var IndexController = function() {
      this.initEvents();
    };

    // add the behaviours defined on the controller
    _.extend( IndexController.prototype, {
      initEvents: function() {
        _.bindAll( this, "onServerModelReady" );
        fui.vent.on( "models.fuseki-server.ready", this.onServerModelReady );
      },

      /** When the fuseki server is ready, we can init the qonsole */
      onServerModelReady: function( event ) {
        // when ready, initialise the qonsole component
        var datasets = fui.models.fusekiServer.datasets();
        var endpoints = {};
        var elem = $("ul.datasets");

        _.each( datasets, function( ds ) {
          var queryURL = ds.queryURL();
          if (queryURL) {
            // TODO this should be moved to a template and made more interesting
            elem.append( sprintf( "<li>%s (with services: %s)</li>\n", ds.name(), ds.serviceTypes().join(", ") ) );
          }
        } );

      }

    } );

    return IndexController;
  }
);
