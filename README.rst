Lightweight Java wrapper around OVH's APIs. Handles all the hard work including credential creation and requests signing.

.. code:: java

	import com.ovh.api.OvhApi;
	
	public class OvhApiTest {
		
		public void testCall() throws OvhApiException {
			String endpoint = "ovh-eu";
			String appKey = "0000000000000000";
			String appSecret = "00000000000000000000000000000000";
			String consumerKey = "00000000000000000000000000000000";
			
			OvhApi api = new OvhApi(endpoint, appKey, appSecret, consumerKey);
			try {
				api.get("/me");
			} catch (OvhApiException e) {
				System.out.prinln(e);
			}
		}
	}
	
The wrapper accepts and returns raw json Strings. You can serialize/deserialize it with any external library, the following example uses Gson from Google.

.. code:: java

	import com.ovh.api.OvhApi;
	import com.google.gson.Gson;
	
	public class OvhApiTest {
		
		public class Me {
		
			public String firstname;
			public String name;
			public String nichandle;
			
			@Override
			public String toString() {
				return "Me [firstname=" + firstname + ", name=" + name + ", nichandle=" + nichandle + "]";
			}
			
		}
		
		public void testCall() throws OvhApiException {
			String endpoint = "ovh-eu";
			String appKey = "0000000000000000";
			String appSecret = "00000000000000000000000000000000";
			String consumerKey = "00000000000000000000000000000000";
			
			OvhApi api = new OvhApi(endpoint, appKey, appSecret, consumerKey);
			try {
				String json = api.get("/me");
				Gson gson = new Gson();
				Me me = gson.fromJson(json, Me.class);
				System.out.println(json);
				System.out.println(me.toString());
			} catch (OvhApiException e) {
				System.out.prinln(e);
			}
		}
	}
	
Configuration
=============

The straightforward way to use OVH's API keys is to embed them directly in the
application code. While this is very convenient, it lacks of elegance and
flexibility.

Alternatively it is suggested to use configuration files or environment
variables so that the same code may run seamlessly in multiple environments.
Production and development for instance.

This wrapper will first look for direct instantiation parameters then
``OVH_ENDPOINT``, ``OVH_APPLICATION_KEY``, ``OVH_APPLICATION_SECRET`` and
``OVH_CONSUMER_KEY`` environment variables. If either of these parameter is not
provided, it will look for a configuration file of the form:

.. code:: ini

    endpoint=ovh-eu
    application_key=my_app_key
    application_secret=my_application_secret
    consumer_key=my_consumer_key

The client will successively attempt to locate this configuration file in

1. Current working directory: ``./ovh.conf``
2. Current user's home directory ``~/.ovh.conf``
3. System wide configuration ``/etc/ovh.conf``

This lookup mechanism makes it easy to overload credentials for a specific
project or user.

Get the sources
---------------

The project is hosted on github and uses gradle as a build system.

.. code:: bash

    git clone https://github.com/ovh/java-ovh.git
    cd java-ovh
    ./gradlew build
    
The compiled library will be at build/libs/java-ovh.jar

You've developed a new cool feature ? Fixed an annoying bug ? We'd be happy
to hear from you !

Run the tests
-------------

.. code:: bash

   ./gradlew test
   
See the report at buid/reports/tests/index.html


Supported APIs
==============

OVH Europe
----------

- **Documentation**: https://eu.api.ovh.com/
- **Community support**: api-subscribe@ml.ovh.net
- **Console**: https://eu.api.ovh.com/console
- **Create application credentials**: https://eu.api.ovh.com/createApp/
- **Create script credentials** (all keys at once): https://eu.api.ovh.com/createToken/

OVH North America
-----------------

- **Documentation**: https://ca.api.ovh.com/
- **Community support**: api-subscribe@ml.ovh.net
- **Console**: https://ca.api.ovh.com/console
- **Create application credentials**: https://ca.api.ovh.com/createApp/
- **Create script credentials** (all keys at once): https://ca.api.ovh.com/createToken/

So you Start Europe
-------------------

- **Documentation**: https://eu.api.soyoustart.com/
- **Community support**: api-subscribe@ml.ovh.net
- **Console**: https://eu.api.soyoustart.com/console/
- **Create application credentials**: https://eu.api.soyoustart.com/createApp/
- **Create script credentials** (all keys at once): https://eu.api.soyoustart.com/createToken/

So you Start North America
--------------------------

- **Documentation**: https://ca.api.soyoustart.com/
- **Community support**: api-subscribe@ml.ovh.net
- **Console**: https://ca.api.soyoustart.com/console/
- **Create application credentials**: https://ca.api.soyoustart.com/createApp/
- **Create script credentials** (all keys at once): https://ca.api.soyoustart.com/createToken/

Kimsufi Europe
--------------

- **Documentation**: https://eu.api.kimsufi.com/
- **Community support**: api-subscribe@ml.ovh.net
- **Console**: https://eu.api.kimsufi.com/console/
- **Create application credentials**: https://eu.api.kimsufi.com/createApp/
- **Create script credentials** (all keys at once): https://eu.api.kimsufi.com/createToken/

Kimsufi North America
---------------------

- **Documentation**: https://ca.api.kimsufi.com/
- **Community support**: api-subscribe@ml.ovh.net
- **Console**: https://ca.api.kimsufi.com/console/
- **Create application credentials**: https://ca.api.kimsufi.com/createApp/
- **Create script credentials** (all keys at once): https://ca.api.kimsufi.com/createToken/

Runabove
--------

- **Community support**: https://community.runabove.com/
- **Console**: https://api.runabove.com/console/
- **Create application credentials**: https://api.runabove.com/createApp/
- **High level SDK**: https://github.com/runabove/python-runabove

License
=======

3-Clause BSD