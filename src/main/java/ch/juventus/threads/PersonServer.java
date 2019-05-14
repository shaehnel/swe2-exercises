package ch.juventus.threads;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PersonServer {

    private PersonStore ps = new PersonStore();

    public static void main(String[] args) {
        PersonServer server = new PersonServer();
        server.start();
    }

    public void start() {

        ExecutorService executor = Executors.newFixedThreadPool(5);

        try (ServerSocket server = new ServerSocket(8888)) {
            while(true) {
                Socket client = server.accept(); // blocking wait!
                executor.submit(new PersonServerTask(client));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        executor.shutdown();
    }

    private class PersonServerTask implements Runnable {

        private Socket client;

        public PersonServerTask(Socket client) {
            this.client = client;
        }

        @Override
        public void run() {

            try (
                    ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
                    ObjectInputStream in = new ObjectInputStream(client.getInputStream());
            ) {
                Request request = (Request) in.readObject();
                System.out.printf("Client %s received %s.\n", client.toString(), request);
                while (!Request.Command.QUIT.equals(request.getCommand())) {
                    switch (request.getCommand()) {
                        case ADD: ps.addPerson(request.getPerson());break;
                        case REMOVE: ps.removePerson(request.getPerson()); break;
                        case GET_BY_NAME:
                            out.writeObject(ps.getPersonsByLastName(request.getLastName()));
                            break;
                        default:
                            System.out.printf("Unknown command %s", request.getCommand());
                    }
                    request = (Request) in.readObject();
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

}
