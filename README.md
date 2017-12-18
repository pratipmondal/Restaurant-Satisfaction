# Restaurant-Satisfaction

Please download the zip file & run it in your local eclipse.

It will run in your SpringBoot Jetty Container.

URL will be - http://localhost:8089/api/getSatisfaction

To Run the Program:
--------------------
1. Do first maven build - mvn clean install. It will create a jar xxx.jar
2. Then run this command - java -jar xxx.jar
3. Otherwise you can right click on App.java and then do run as Java Application.
4. It will start the Jetty server on 8089 port.
5. Test with http://localhost:8089/api/getSatisfaction. It will return the Satsifaction Amount in browser / soapui response.

Program:
--------------------
private void move() {
		for(int z = dots; z>0;z--) {
			if(z>4 && x[0]==x[z] && y[0]==y[z]) {
				inGame = false;
			}
		}
		
		if(y[0] >= B_HEIGHT)
			inGame = false;
		if(y[0] < 0)
			inGame = false;
		if(x[0] >= B_WIDTH)
			inGame = false;
		if(x[0] < 0)
			inGame = false;
		
		if(!inGame) {
			timer.stop();
		}
		
		for(int z = dots; z>0;z--) {
			x[z] = x[z-1];
			y[z] = y[z-1];
		}
		
		for(int i=0; i<1;i++) {
			if(apple_y > y[0]) {
				go_up = true;
			}else if(apple_y < y[0]) {
				go_up = false;
			}
			if(x[0] < 290 && change_y == 0) {
				x[0] += DOT_SIZE;
				if(x[0] == 290) change_y = 1;
				break;
			}
			if(go_up) {
				if(change_y == 1) {
					y[0] += DOT_SIZE;
					if(apple_y == y[0])change_y = 2;
					break;
				}
				if(change_y == 2) {
					x[0] -= DOT_SIZE;
					if(x[0] == 0)  change_y = 3;
					break;
				}
				if(change_y == 3) {
					y[0] += DOT_SIZE;
					change_y = 4;
					break;
				}
				if(change_y == 4) {
					x[0] += DOT_SIZE;
					change_y = 0;
					break;
				}
			}
			if(!go_up) {
				if(change_y == 1) {
					y[0] -= DOT_SIZE;
					if(apple_y == y[0])change_y = 2;
					break;
				}
				if(change_y == 2) {
					x[0] -= DOT_SIZE;
					if(x[0] == 0)  change_y = 3;
					break;
				}
				if(change_y == 3) {
					y[0] -= DOT_SIZE;
					change_y = 4;
					break;
				}
				if(change_y == 4) {
					x[0] += DOT_SIZE;
					change_y = 0;
					break;
				}
			}
		}
	}

