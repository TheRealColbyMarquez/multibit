/**
 * Copyright 2011 multibit.org
 *
 * Licensed under the MIT license (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://opensource.org/licenses/mit-license.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.multibit.message;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Enum singleton to manage MultiBit (and in future system wide) messages.
 * @author jim
 */
public enum MessageManager {
    INSTANCE;
    
    /**
     * Queue containing Messages received.
     */
    private LinkedBlockingQueue<Message> messageQueue;
    
    /**
     * Message listeners.
     */
    private Collection<MessageListener> messageListeners;
    
    MessageManager() {
        messageQueue = new LinkedBlockingQueue<Message>();
        messageListeners = new ArrayList<MessageListener>();
    }
    
    synchronized public void addMessage(Message message) {
        if (message != null) {
            messageQueue.add(message);
            notifyMessageListeners(message);
        }
    }
    
    synchronized public void clearAllMessages() {
        messageQueue.clear();
    }
    
    public void addMessageListener(MessageListener messageListener) {
        messageListeners.add(messageListener);
    }
    
    public void removeMessageListener(MessageListener messageListener) {
        messageListeners.remove(messageListener);
    }
    
    private void notifyMessageListeners(Message message) {
        for (MessageListener listener : messageListeners) {
            listener.newMessageReceived(message);
        }
    }
    
    public Collection<Message> getMessages() {
        return Collections.unmodifiableCollection(messageQueue);
    }   
}
