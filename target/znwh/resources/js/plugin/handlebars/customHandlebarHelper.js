/**
 * Created by psf on 2017/11/24.
 */

var handlebarHelper = function ($) {
  /*  var initHandlebars = function () {*/
        Handlebars.registerHelper("compare", function (v1, v2, options) {
            if (v1 == v2) {
                return options.fn(this);
            } else {
                return options.inverse(this);
            }
        });

        Handlebars.registerHelper('moreThan', function (v1, v2, options) {
            if (v1 > v2) {
                return options.fn(this);
            } else {
                return options.inverse(this);
            }
        });

        Handlebars.registerHelper('list', function (items, options) {
            var out = "<ul>";

            for (var i = 0, l = items.length; i < l; i++) {
                out = out + "<li>" + options.fn(items[i]) + "</li>";
            }

            return out + "</ul>";
        });

        Handlebars.registerHelper('formatDate', function (time, format, options) {
            if (arguments.length > 2) {
                format = arguments[1];
            }
            if (time) {
                return (new Date(time)).Format(format);
            } else {
                return '';
            }
        });



  /*  initHandlebars();


    return {
        init: function () {
            console.log("加载自定义helper......");
        }
    }*/
}(jQuery);

