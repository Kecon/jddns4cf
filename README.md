# jddns4cf
Dynamic DNS for Cloudflare® in Java.

The application is supposed to be running on machines that have DHCP assigned IP addresses and want to update a dynamic DNS record in Cloudflare®. The program has been tested on both Windows 10 and Ubuntu Linux 16.04 LTS.

## Requirements
Java 1.8

## Configuration example
The configuration is stored in a JSON file and is supplied as an argument when launching the application.

```json
{
	"authEmail":"user@example.com",
	"authKey":"abcdef123456789abcdef1235678890",
	"interfaceName":"eth0",
	"type":"A",
	"name":"example.com",
	"zoneId":"098765432abcdefg0987654321"
}
```

You may list all available interface names using "-i" as single argument.

### Run as a service in systemd
This file could be placed in /lib/systemd/system/jddns4cf.service if you would like to run this application as a service in Ubuntu Linux 16.04 LTS with systemd. Make sure that paths to both the .jar and config.json are correct.

```
[Unit]
Description=jddns4cf
[Service]
WorkingDirectory=/usr/lib/jddns4cf
ExecStart=/usr/bin/java -jar /usr/lib/jddns4cf/jddns4cf-0.0.1-SNAPSHOT-jar-with-dependencies.jar /etc/jddns4cf/config.json
Type=simple
#User=jddns4cf (optional user)
[Install]
WantedBy=multi-user.target
```

Start service and add to auto start:
```
systemctl start jddns4cf
systemctl enable jddns4cf
```

# Trademarks
Cloudflare is a registered trademark of Cloudflare, Inc
