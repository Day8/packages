
/**
 * @fileoverview Closure Compiler externs for raygun4js
 * @see https://github.com/MindscapeHQ/raygun4js
 * @externs
 */

/** @type {Object} */
var TraceKit;

/** @type {Object} */
var Raygun;


/**
 * @param {string} 			    key
 * @param {?Object}	     	    options
 * @param {?Object|function()} customdata
 * @return {Object}
 */
Raygun.init = function(key, options, customdata) {};

/**
 * @param {Object|function()} customdata
 * @return {Object}
 */
Raygun.withCustomData = function (customdata) {};

/**
 * @param {string[]|function()} tags
 * @return {Object}
 */
Raygun.withTags = function (tags) {};


/** @return {Object} */
Raygun.attach = function () {};


/** @return {Object} */
Raygun.detach = function () {};

/**
 * @param {*} 		  		   ex
 * @param {?Object|function()} customdata
 * @param {?string[]} 		   tags
 * @return {Object}
 */
Raygun.send = function(ex, customData, tags) {};

/**
 * @param {string}   user
 * @param {?boolean} isAnonymous
 * @param {?string}  email
 * @param {?string}  fullName
 * @param {?string}  firstName
 * @param {?string}  uuid
 * @return {Object}
 */
Raygun.setUser = function (user, isAnonymous, email, fullName, firstName, uuid) {};


Raygun.resetAnonymousUser = function () {};

/**
 * @param {string} version
 * @return {Object}
 */
Raygun.setVersion = function(version) {};

/**
 * @param {?boolean} enableOffline
 * @return {Object}
 */
Raygun.saveIfOffline = function(enableOffline) {};

/**
 * @param {string[]} filteredKeys
 * @return {Object}
 */
Raygun.filterSensitiveData = function(filteredKeys) {};

/**
 * @param {string} scope - 'customData' || 'all'
 * @return {Object}
 */
Raygun.setFilterScope = function(scope) {};

/**
 * @param {string[]} whitelist - array of url
  * @return {Object}
 */
Raygun.whitelistCrossOriginDomains = function(whitelist) {};

/**
 * @callback callback
 * @param {Object}
 * @return {Object}
 */
Raygun.onBeforeSend = function(callback) {};




