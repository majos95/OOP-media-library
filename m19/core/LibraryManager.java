package m19.core;

import java.io.IOException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;
import java.io.ObjectInputStream;

import m19.app.exception.NoSuchUserException;
import m19.app.exception.NoSuchWorkException;
import m19.app.exception.RuleFailedException;
import m19.app.exception.UserIsActiveException;
import m19.app.exception.UserRegistrationFailedException;
import m19.app.exception.WorkNotBorrowedByUserException;
import m19.core.exception.MissingFileAssociationException;
import m19.core.exception.RuleViolationException;
import m19.core.exception.WorkNotBorrowedException;
import m19.core.exception.BadEntrySpecificationException;
import m19.core.exception.ImportFileException;

/**
 * The façade class.
 */
public class LibraryManager implements Serializable {
	
private static final long serialVersionUID = 201901101348L;

  private Library _library = new Library(); 
  private String _fileName;
  
  
  
  	public LibraryManager() {
	  _fileName = "";
	}
  
  	public String getFileName() {
		return _fileName;
	}
  	
  	public Library getLibrary() {
  		return _library;
  	}
  	public void setFileName(String file) {
		_fileName = file;
	}

  /**
   * Serialize the persistent state of this application.
   * 
   * @throws MissingFileAssociationException if the name of the file to store the persistent
   *         state has not been set yet.
   * @throws IOException if some error happen during the serialization of the persistent state

   */
  
  	
  	public void save() throws MissingFileAssociationException, IOException {
		if((this.getFileName()).equals("")) {
			throw new MissingFileAssociationException();
		}
		FileOutputStream fileOut = null;
		ObjectOutputStream out = null;
		try {
			fileOut = new FileOutputStream(this.getFileName());
			out = new ObjectOutputStream(fileOut);
			out.writeObject(this.getLibrary());
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if (out != null) {
					out.close();
				}
				if (fileOut != null) {
					fileOut.close();
				}
			} catch (Exception e) {
				throw e;
			}
		}	    
	}

  /**
   * Serialize the persistent state of this application into the specified file.
   * 
   * @param filename the name of the target file
   *
   * @throws MissingFileAssociationException if the name of the file to store the persistent
   *         is not a valid one.
   * @throws IOException if some error happen during the serialization of the persistent state
   */
  public void saveAs(String filename) throws MissingFileAssociationException, IOException {
	  FileOutputStream fileOut = null;
	  ObjectOutputStream out = null;
	  try {
		  fileOut = new FileOutputStream(filename);
		  out = new ObjectOutputStream(fileOut);
		  this.setFileName(filename);
		  out.writeObject(_library);
	  } catch (Exception e) {
			throw e;
		} finally {
			try {
				if (out != null) {
					out.close();
				}
				if (fileOut != null) {
					fileOut.close();
				}
			} catch (Exception e) {
				throw e;
			}
		}
  }
  /**
   * Recover the previously serialized persitent state of this application.
   * 
   * @param filename the name of the file containing the perssitente state to recover
   *
   * @throws IOException if there is a reading error while processing the file
   * @throws FileNotFoundException if the file does not exist
   * @throws ClassNotFoundException 
 * @throws ImportFileException 
   */
  public void load(String filename) throws FileNotFoundException, IOException, ClassNotFoundException, ImportFileException {
	 FileInputStream fileIn = null;
	 ObjectInputStream in = null;
		try {
			fileIn = new FileInputStream(filename);
			in = new ObjectInputStream(fileIn);
			_library = (Library)in.readObject();
			_fileName = filename;
		} catch (FileNotFoundException e) {
			throw e;
		} finally {
			try {
				if (in != null) {
					in.close();
				}
				if (fileIn != null) {
					fileIn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	  
  }

  /**
   * Set the state of this application from a textual representation stored into a file.
   * 
   * @param datafile the filename of the file with the textual represntation of the state of this application.
   * @throws ImportFileException if it happens some error during the parsing of the textual representation.
   */
  public void importFile(String datafile) throws ImportFileException {
    try {
      _library.importFile(datafile);
    } catch (IOException | BadEntrySpecificationException e) {
      throw new ImportFileException(e);
    }
  }
	
	public int getCurrentDate() {
		return _library.getCurrentDate();
	}
	
	public void advanceDays(Integer value) {
		_library.advanceDays(value);
		
	}
	
	public User registerUser(String userName, String eMail) throws UserRegistrationFailedException {
		for (User user: _library.getUsers()) {
			if (user.getEmail().compareTo(eMail) == 0) {
				throw new UserRegistrationFailedException(userName, eMail);
			}
		}
		if ("".equals(userName) || "".equals(eMail)) {
			throw new UserRegistrationFailedException(userName, eMail);
		} 
		else if(userName.equals(eMail)){
			throw new UserRegistrationFailedException(userName, eMail);
		}
		return _library.registerNewUser(userName, eMail);
	}
	
	public List<User> getUsers() {
		return Collections.unmodifiableList(_library.getOrderedUsers());
	}
	
	public User getUser(Integer id) throws NoSuchUserException {
		int numUsers = _library.totalUsers();

		if(id >= numUsers || id < 0) {
			throw new NoSuchUserException(id);
		}

		return _library.getUsers().get(id);
	}
	
	public List<Notification> getUserNotifications(Integer userId) throws NoSuchUserException {
		try {
			User user = this.getUser(userId);
			return _library.getUserNotifications(user);
		} catch(NoSuchUserException e) {
			throw new NoSuchUserException(userId);
		}
	}
	
	public void removeUserNotifications (Integer userId) throws NoSuchUserException {
		try {
			User user = this.getUser(userId);
			_library.removeUserNotifications(user);
		} catch(NoSuchUserException e) {
			throw new NoSuchUserException(userId);
		}
	}
	
	
	public Work getWork(Integer id) throws NoSuchWorkException {
		int numWorks = _library.totalWorks();
		
		if(id >= numWorks || id < 0) {
			throw new NoSuchWorkException(id);
		}
		
		return _library.getWorks().get(id);
	}
	
	public List<Work> getAllWorks() {
		return Collections.unmodifiableList(_library.getWorks());	
	}
	
	
	public List<Work> searchWorks(String searchTearm) {
		List<Work> _results = new LinkedList<>();
		List<Work> _works = _library.getWorks();

		for(Work work: _works) {
			String title = work.getTitle();
			String author_director = "";
			if( work instanceof Dvd) {
				Dvd dvd = (Dvd) work;
				author_director += dvd.getDirector();
				if(Pattern.compile(Pattern.quote(searchTearm), Pattern.CASE_INSENSITIVE).matcher(author_director).find() || Pattern.compile(Pattern.quote(searchTearm), Pattern.CASE_INSENSITIVE).matcher(title).find()) {
					_results.add(work);
				}
			}
			else if( work instanceof Book) {
				Book book = (Book) work;
				author_director += book.getAuthor();	
				if(Pattern.compile(Pattern.quote(searchTearm), Pattern.CASE_INSENSITIVE).matcher(author_director).find() || Pattern.compile(Pattern.quote(searchTearm), Pattern.CASE_INSENSITIVE).matcher(title).find()) {
					_results.add(work);
				}
			}
		}

		return _results;
	}
	
	public int requestWork(int UserId, int WorkId) throws RuleFailedException, NoSuchUserException, NoSuchWorkException {
		try {
			User user = this.getUser(UserId);
			Work work = this.getWork(WorkId);
			_library.checkRules(user, work);
		} catch(RuleViolationException e) {
			if (e.getIndex() != 3) {
				throw new RuleFailedException(UserId, WorkId, e.getIndex());
			} else {
				return -1;
			}	
		} catch(NoSuchUserException e) {
			throw new NoSuchUserException(UserId);
		} catch(NoSuchWorkException e) {
			throw new NoSuchWorkException(WorkId);
		}
		
		 return _library.addRequest(UserId, WorkId);

		
		
	}

	public int returnWork(Integer userId, Integer workId) throws NoSuchUserException, NoSuchWorkException, WorkNotBorrowedByUserException {
		try {
			User user = this.getUser(userId);
			Work work = this.getWork(workId);
			return _library.returnWork(user, work);
		}catch (NoSuchUserException e) {
			throw new NoSuchUserException(userId);
		}catch (NoSuchWorkException e) {
			throw new NoSuchWorkException(workId);
		}catch (WorkNotBorrowedException e) {
			throw new WorkNotBorrowedByUserException(workId, userId);
		}
		
	}

	public void payFine(Integer userId) throws UserIsActiveException, NoSuchUserException {
		try {
			User user = this.getUser(userId);
			if(user.isActive()) {
				throw new UserIsActiveException(userId);
			}
			user.payFine(_library.getCurrentDate());
		} catch (NoSuchUserException e) {
			throw new NoSuchUserException(userId);
		}

	}
	
	


	public void warnUserWhenWorkIsAvailable(Integer userId, Integer workId) {
		_library.addWorksUserWantsNotification(userId, workId);
		
	}
	
	public void checkUsersWorksNotifications(Integer workId) throws NoSuchWorkException {
		try {
			Work work = this.getWork(workId);
			_library.chekcUserWorksNotification(work);
		} catch (NoSuchWorkException e){
			throw new NoSuchWorkException (workId);
		}
	}

	public List<User> getUsersWithRequest(Integer workId) throws NoSuchWorkException {
		List<User> results = new LinkedList<>();
		List<User>  users = this.getUsers();
		try {
			Work work = this.getWork(workId);
			for (User user: users) {
				for (Request request: user.getRequests()) {
					if(request.getWork().equals(work)) {
						results.add(user);
					}
				}
			}
			Comparator <User> compareByMail = new Comparator<User>() {
				public int compare(User one, User two) {
					return one.getEmail().compareTo(two.getEmail());
				}
			};
			Collections.sort(results, compareByMail);
			return results;	
			
		} catch (NoSuchWorkException e){
			throw new NoSuchWorkException (workId);
		}
				
	}

	public List<Work> augmentPrice(String searchTearm) throws BadEntrySpecificationException {
		List<Work> works = this.getAllWorks();
		List<Work> results = new LinkedList<>();
		
		if(works.size()== 0) {
			throw new BadEntrySpecificationException("nao existem obras com tal string");
		}
		
		for(Work work: works) {
			if(work.getTitle().contains(searchTearm)) {
				work.augmentPrice(10);
				results.add(work);
			}
		}
		if(results.size()== 0) {
			throw new BadEntrySpecificationException("nao existem obras com tal string");
		}
		return results;
	}

	public User getMostValuableUser() throws BadEntrySpecificationException {
		List<User> users  = this.getUsers();
		if(users.size() == 0) {
			throw new BadEntrySpecificationException("Não existem users");
		}
		int maxNumRequests = 0;
		int maxId = 0;
		
		for(User user: users) {
			if(user.numRequests() > maxNumRequests) {
				maxNumRequests = user.numRequests();
				maxId = user.getId();
			}
			else if(user.numRequests() == maxNumRequests && user.getId() > maxId) {
				maxNumRequests = user.numRequests();
				maxId = user.getId();
			}
		}
		
		try {
			return this.getUser(maxId);
		} catch (NoSuchUserException e) {
			e.printStackTrace();
		}
		return null;
	}	
}
