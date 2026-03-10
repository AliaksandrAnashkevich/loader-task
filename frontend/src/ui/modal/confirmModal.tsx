type ConfirmModalProps = {
  isOpen: boolean;
  title?: string;
  description?: string;
  onConfirm: () => void;
  onClose: () => void;
};

const ConfirmModal = ({
  isOpen,
  title = "Подтверждение действия",
  description = "Вы уверены, что хотите выполнить это действие?",
  onConfirm,
  onClose,
}: ConfirmModalProps) => {
  if (!isOpen) return null;

  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center bg-black/40">
      <div className="w-full max-w-md rounded-2xl bg-white p-6 shadow-xl">
        <h2 className="text-lg font-semibold text-gray-800">{title}</h2>

        <p className="mt-2 text-sm text-gray-500">{description}</p>

        <div className="mt-6 flex justify-end gap-3">
          <button
            onClick={onClose}
            className="rounded-lg border border-gray-300 px-4 py-2 text-sm hover:bg-gray-100"
          >
            Отмена
          </button>

          <button
            onClick={onConfirm}
            className="rounded-lg bg-red-500 px-4 py-2 text-sm text-white hover:bg-red-600"
          >
            Удалить
          </button>
        </div>
      </div>
    </div>
  );
};

export default ConfirmModal;
