Camel Cache Client Example
==========================
Example client that interfaces with the camel-cache-example project.

Build
-----
mvn clean install

Install
-------
*Required features to install on JBoss Fuse:*
- camel-core
- camel-blueprint

osgi:install -s mvn:com.redhat.examples/camel-cache-client/1.0.0-SNAPSHOT

Use
---
Once the bundle is deployed it starts a timer that fires twice. On the first trigger it will try to SELECT a value from the cache based on the key "jboss". On the second trigger it will UPDATE or ADD the value "EAP" for the key jboss. It interfaces with the camel-cache-example project using direct:vm endpoints because of a bug in Karaf that shutsdown the cache if any routes containing the cache endpoint are shutdown.
