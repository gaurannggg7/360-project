package healthcare;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import actors.*;

public class MessagingManagement {
    public void sendMessage(int senderId, int receiverId, String messageContent) {
        String sql = "INSERT INTO messages (sender_id, receiver_id, message_text, timestamp) VALUES (?, ?, ?, NOW())";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, senderId);
            stmt.setInt(2, receiverId);
            stmt.setString(3, messageContent);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static List<Message> getMessages(int userId, int otherUserId) {
        List<Message> messages = new ArrayList<>();
        String sql = "SELECT * FROM messages WHERE (sender_id = ? AND receiver_id = ?) OR (sender_id = ? AND receiver_id = ?) ORDER BY timestamp";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, otherUserId);
            stmt.setInt(3, otherUserId);
            stmt.setInt(4, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Message message = new Message(
                    rs.getInt("sender_id"),
                    rs.getInt("receiver_id"),
                    rs.getString("message_text"),
                    rs.getTimestamp("timestamp").getTime()
                );
                messages.add(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return messages;
    }
}